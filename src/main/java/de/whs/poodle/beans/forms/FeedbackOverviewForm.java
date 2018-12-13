/*
 * Copyright 2015 Westfälische Hochschule
 *
 * This file is part of Poodle.
 *
 * Poodle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Poodle is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Poodle.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.whs.poodle.beans.forms;

/*
 * Form used to in the FeedbackOverview.
 */
public class FeedbackOverviewForm {

    public enum VisibleValue {
        DIFFICULTY, TIME, FUN, COMPLETED
    }

    private int courseTermId;
    private VisibleValue value;

    public FeedbackOverviewForm() {
        this.value = VisibleValue.TIME;
    }

    public int getCourseTermId() {
        return courseTermId;
    }

    public void setCourseTermId(int courseTermId) {
        this.courseTermId = courseTermId;
    }

    public VisibleValue getValue() {
        return value;
    }

    public void setValue(VisibleValue value) {
        this.value = value;
    }
}
