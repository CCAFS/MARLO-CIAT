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
import org.cgiar.ccafs.marlo.data.model.ProjectSectionsEnum;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.utils.InvalidFieldsMessages;
import org.cgiar.ccafs.marlo.validation.BaseValidator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import com.google.inject.Inject;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ProjectDescriptionValidator extends BaseValidator {

  private ICenterService centerService;

  @Inject
  public ProjectDescriptionValidator(ICenterService centerService) {
    this.centerService = centerService;
  }

  private Path getAutoSaveFilePath(Project project, long centerID) {
    ResearchCenter center = centerService.getCrpById(centerID);
    String composedClassName = project.getClass().getSimpleName();
    String actionFile = ProjectSectionsEnum.DESCRIPTION.getStatus().replace("/", "_");
    String autoSaveFile =
      project.getId() + "_" + composedClassName + "_" + center.getAcronym() + "_" + actionFile + ".json";

    return Paths.get(config.getAutoSaveFolder() + autoSaveFile);
  }

  public void validate(BaseAction baseAction, Project project, ResearchProgram selectedProgram, boolean saving) {

    baseAction.setInvalidFields(new HashMap<>());

    if (!saving) {
      Path path = this.getAutoSaveFilePath(project, baseAction.getCrpID());

      if (path.toFile().exists()) {
        this.addMissingField("programImpact.action.draft");
      }
    }

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
      baseAction.getInvalidFields().put("input-project.projectLeader.composedName", InvalidFieldsMessages.EMPTYFIELD);
    }

    // TODO project.getProjectCrosscutingTheme() Validation


    if (project.getFundingSources() == null || project.getFundingSources().isEmpty()) {
      this.addMessage(baseAction.getText("projectDescription.action.fundingSources"));
      baseAction.getInvalidFields().put("list-project.fundingSources",
        baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"Funding source"}));
    } else {
      for (int i = 0; i < project.getFundingSources().size(); i++) {
        this.validateFundingSource(baseAction, project.getFundingSources().get(i), i);
      }
    }

    if (project.getOutputs() == null || project.getOutputs().isEmpty()) {
      this.addMessage(baseAction.getText("projectDescription.actio.outputs"));
      baseAction.getInvalidFields().put("list-project.outputs",
        baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"Outputs"}));
    }


  }


}
