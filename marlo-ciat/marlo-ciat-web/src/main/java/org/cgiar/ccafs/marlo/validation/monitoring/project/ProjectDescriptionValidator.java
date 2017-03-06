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
import org.cgiar.ccafs.marlo.data.model.ProjectFundingSource;
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

  public void validateFundingSource(BaseAction baseAction, ProjectFundingSource fundingSource, int i) {
    if (fundingSource.getFundingSourceType() == null) {
      this.addMessage(
        baseAction.getText("projectDescription.action.fundingSources.fundingSourceType.id", String.valueOf(i + 1)));
      baseAction.getInvalidFields().put("input-project.fundingSources[" + i + "].fundingSourceType.id",
        InvalidFieldsMessages.EMPTYFIELD);
    } else {
      if (fundingSource.getFundingSourceType().getId() == null || fundingSource.getFundingSourceType().getId() == -1) {
        this.addMessage(
          baseAction.getText("projectDescription.action.fundingSources.fundingSourceType.id", String.valueOf(i + 1)));
        baseAction.getInvalidFields().put("input-project.fundingSources[" + i + "].fundingSourceType.id",
          InvalidFieldsMessages.EMPTYFIELD);
      }
    }

    if (!this.isValidString(fundingSource.getDonor()) && this.wordCount(fundingSource.getDonor()) <= 100) {
      this.addMessage(
        baseAction.getText("projectDescription.action.fundingSources.fundingSourceType.donor", String.valueOf(i + 1)));
      baseAction.getInvalidFields().put("input-project.fundingSources[" + i + "].fundingSourceType.donor",
        InvalidFieldsMessages.EMPTYFIELD);
    }
  }


  public void validateProjectDescription(BaseAction baseAction, Project project) {

    if (!this.isValidString(project.getName()) && this.wordCount(project.getName()) <= 150) {
      this.addMessage(baseAction.getText("projectDescription.action.title"));
      baseAction.getInvalidFields().put("input-project.name", InvalidFieldsMessages.EMPTYFIELD);
    }

    if (!this.isValidString(project.getShortName()) && this.wordCount(project.getShortName()) <= 50) {
      this.addMessage(baseAction.getText("projectDescription.action.shortName"));
      baseAction.getInvalidFields().put("input-project.shortName", InvalidFieldsMessages.EMPTYFIELD);
    }

    if (project.getStartDate() == null) {
      this.addMessage(baseAction.getText("projectDescription.action.startDate"));
      baseAction.getInvalidFields().put("input-project.startDate", InvalidFieldsMessages.EMPTYFIELD);
    }
    if (project.getEndDate() == null) {
      this.addMessage(baseAction.getText("projectDescription.action.endDate"));
      baseAction.getInvalidFields().put("input-project.endDate", InvalidFieldsMessages.EMPTYFIELD);
    }

    if (project.getProjectLeader() == null) {
      this.addMessage(baseAction.getText("projectDescription.action.projectLeader"));
      baseAction.getInvalidFields().put("input-project.projectLeader", InvalidFieldsMessages.EMPTYFIELD);
    }

    // TODO project.getProjectCrosscutingTheme() Validation


    if (project.getFundingSources() == null || project.getFundingSources().isEmpty()) {
      this.addMessage(baseAction.getText("projectDescription.action.fundingSources"));
      baseAction.getInvalidFields().put("list-project.fundingSources",
        baseAction.getText(InvalidFieldsMessages.EMPTYLIST));
    } else {
      for (int i = 0; i < project.getFundingSources().size(); i++) {
        this.validateFundingSource(baseAction, project.getFundingSources().get(i), i);
      }
    }

    if (project.getOutputs() == null || project.getOutputs().isEmpty()) {
      this.addMessage(baseAction.getText("projectDescription.actio.outputs"));
      baseAction.getInvalidFields().put("list-project.outputs", baseAction.getText(InvalidFieldsMessages.EMPTYLIST));
    }


  }


}
