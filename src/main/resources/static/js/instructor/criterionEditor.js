$(document).ready(function () {
    /* global exercise */
    /* global utils */
    "use strict";

    function addDelButton(row) {
        var $row = $(row);
        var testId = $row.attr("data-test-id");

        if (typeof testId !== typeof undefined && testId !== false) {
            var $td = $("<td>")
                .prop("class", "btnTd");

            var $btn = $("<button>")
                .prop("class", "deleteTestButton");

            $btn.append($("<span>")
                .prop("class", "glyphicon glyphicon-remove"));

            $btn.click(function (e) {
                deleteTest($row, testId);
                e.preventDefault();
            });

            $td.append($btn);
            $row.append($td);
        }
    }

    function createTest(test) {
        criterionEditor.createTest(test)
            .done(function (test) {
                /* create a new element for the test table (matching criterionTable.html #testTable) */
                var $newRow = $("<tr>");
                $newRow.attr("data-test-id", test.id);

                $newRow.append($("<td>").text(test.input));
                $newRow.append($("<td>").text(test.output));
                $newRow.append($("<td>")
                    .prop("class", "centeredTd")
                    .append($("<input>")
                        .prop("type", "checkbox")
                        .prop("disabled", "disabled")
                        .prop("class", "readOnlyCB")
                        .prop("checked", test.isHidden)));

                addDelButton($newRow);

                // add new element to table
                $("#testTable").append($newRow);

                // empty input fields
                $("#newTestInput").val("");
                $("#newTestOutput").val("");
                $("#newTestIsHidden").prop("checked", false);

                utils.showOkMessage(messages.testCreated);
            });
    }

    function deleteTest(row, testId) {
        criterionEditor.deleteTest(testId)
            .done(function () {
                $(row).remove();

                utils.showOkMessage(messages.testDeleted);
            });
    }

    // add "delete test" buttons
    $("#testTable > tbody > tr").each(function (i, row) {
        addDelButton(row);
    });

    // "create test" button
    $("#createTestButton").click(function (e) {
        var test = {
            input: $("#newTestInput").val(),
            output: $("#newTestOutput").val(),
            isHidden: $("#newTestIsHidden").prop("checked"),
            suiteId: $("#newTestSuiteId").val()
        };

        createTest(test);
        e.preventDefault();
    });

    // confirm suite deletion
    $("#deleteForm").submit(function () {
        return confirm(messages.reallyDeleteAllTests);
    })
});

var criterionEditor = (function () {
    /* global utils */
    "use strict";

    return {
        createTest: function (test) {
            return $.ajax({
                url: utils.contextPath + "criterion/rest/tests/",
                data: test,
                type: "POST"
            });
        },

        deleteTest: function (testId) {
            return $.ajax({
                url: utils.contextPath + "criterion/rest/tests/" + testId,
                type: "DELETE"
            });
        }
    };
})();