/*****************************************************************
 * This file is part of Managing Agricultural Research for Learning &
 * Outcomes Platform (MARLO).
 * MARLO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * MARLO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with MARLO. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

package org.cgiar.ccafs.marlo.validation.monitoring.project;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.utils.InvalidFieldsMessages;
import org.cgiar.ccafs.marlo.validation.BaseValidator;

import java.util.HashMap;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ProjectDescriptionValidator extends BaseValidator {

  public void validate(BaseAction baseAction, Project project, ResearchProgram selectedProgram, boolean saving) {

    baseAction.setInvalidFields(new HashMap<>());

    // TODO Autosave Draft validator.

    if (!baseAction.getFieldErrors().isEmpty()) {
      baseAction.addActionError(baseAction.getText("saving.fields.required"));
    }

    this.validateProjectDescription(baseAction, project);

    this.saveMissingFields(selectedProgram, project, "projectDescription");

  }

  public void validateProjectDescription(BaseAction baseAction, Project project) {

    if (project.getFundingSources() == null || project.getFundingSources().isEmpty()) {
      this.addMessage(baseAction.getText("outcome.action.milestones"));
      baseAction.getInvalidFields().put("list-outcome.milestones",
        baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"Milestones"}));
    } else {
      for (int i = 0; i < project.getFundingSources().size(); i++) {
        // TODO
        // this.validateMilestones(baseAction, outcome.getMilestones().get(i), i);
      }

    }

  }


}
