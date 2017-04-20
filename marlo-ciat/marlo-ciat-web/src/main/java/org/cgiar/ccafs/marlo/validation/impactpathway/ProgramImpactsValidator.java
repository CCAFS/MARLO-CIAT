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

package org.cgiar.ccafs.marlo.validation.impactpathway;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.data.model.ImpactPathwaySectionsEnum;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchImpact;
import org.cgiar.ccafs.marlo.data.model.ResearchImpactBeneficiary;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.utils.InvalidFieldsMessages;
import org.cgiar.ccafs.marlo.validation.BaseValidator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.inject.Inject;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ProgramImpactsValidator extends BaseValidator {

  ICenterService centerService;

  @Inject
  public ProgramImpactsValidator(ICenterService centerService) {
    this.centerService = centerService;
  }

  private Path getAutoSaveFilePath(ResearchProgram program, long centerID) {
    ResearchCenter center = centerService.getCrpById(centerID);
    String composedClassName = program.getClass().getSimpleName();
    String actionFile = ImpactPathwaySectionsEnum.PROGRAM_IMPACT.getStatus().replace("/", "_");
    String autoSaveFile =
      program.getId() + "_" + composedClassName + "_" + center.getAcronym() + "_" + actionFile + ".json";

    return Paths.get(config.getAutoSaveFolder() + autoSaveFile);
  }

  public void validate(BaseAction baseAction, List<ResearchImpact> researchImpacts, ResearchProgram selectedProgram,
    boolean saving) {

    baseAction.setInvalidFields(new HashMap<>());

    if (!saving) {
      Path path = this.getAutoSaveFilePath(selectedProgram, baseAction.getCrpID());

      if (path.toFile().exists()) {
        this.addMissingField("programImpact.action.draft");
      }
    }


    if (!baseAction.getFieldErrors().isEmpty()) {
      baseAction.addActionError(baseAction.getText("saving.fields.required"));
    }

    if (researchImpacts.size() == 0) {
      this.addMessage(baseAction.getText("programImpact.action.required"));
      baseAction.getInvalidFields().put("list-researchImpacts",
        baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"Program Impacts"}));

    }

    for (int i = 0; i < researchImpacts.size(); i++) {

      ResearchImpact researchImpact = researchImpacts.get(i);
      this.validateProgramImpact(baseAction, researchImpact, i);
    }

    this.saveMissingFields(selectedProgram, "programimpacts");
  }

  public void validateBeneficiaries(BaseAction baseAction, ResearchImpactBeneficiary impactBeneficiary, int i, int j) {

    List<String> params = new ArrayList<String>();
    params.add(String.valueOf(i + 1));
    params.add(String.valueOf(j + 1));

    if (impactBeneficiary.getResearchRegion() != null) {
      if (impactBeneficiary.getResearchRegion().getId() == -1) {
        this.addMessage(baseAction.getText("programImpact.action.beneficiary.region", params));
        baseAction.getInvalidFields().put("input-impacts[" + i + "].beneficiaries[" + j + "].researchRegion.id",
          InvalidFieldsMessages.EMPTYFIELD);
      }
    } else {
      this.addMessage(baseAction.getText("programImpact.action.beneficiary.region", params));
      baseAction.getInvalidFields().put("input-impacts[" + i + "].beneficiaries[" + j + "].researchRegion.id",
        InvalidFieldsMessages.EMPTYFIELD);
    }

    if (impactBeneficiary.getBeneficiary() != null) {
      if (impactBeneficiary.getBeneficiary().getId() == -1) {
        this.addMessage(baseAction.getText("programImpact.action.beneficiary.focus", params));
        baseAction.getInvalidFields().put("input-impacts[" + i + "].beneficiaries[" + j + "].beneficiary.id",
          InvalidFieldsMessages.EMPTYFIELD);
      }
    } else {
      this.addMessage(baseAction.getText("programImpact.action.beneficiary.focus", params));
      baseAction.getInvalidFields().put("input-impacts[" + i + "].beneficiaries[" + j + "].beneficiary.id",
        InvalidFieldsMessages.EMPTYFIELD);
    }

    if (impactBeneficiary.getBeneficiary() != null) {
      if (impactBeneficiary.getBeneficiary().getBeneficiaryType().getId() == -1) {
        this.addMessage(baseAction.getText("programImpact.action.beneficiary.type", params));
        baseAction.getInvalidFields().put(
          "input-impacts[" + i + "].beneficiaries[" + j + "].beneficiary.beneficiaryType.id",
          InvalidFieldsMessages.EMPTYFIELD);
      }
    }
  }

  public void validateProgramImpact(BaseAction baseAction, ResearchImpact researchImpact, int i) {

    List<String> params = new ArrayList<String>();
    params.add(String.valueOf(i + 1));

    if (researchImpact.getDescription() != null) {
      if (!this.isValidString(researchImpact.getDescription())
        && this.wordCount(researchImpact.getDescription()) >= 150) {
        this.addMessage(baseAction.getText("programImpact.action.description.required", params));
        baseAction.getInvalidFields().put("input-impacts[" + i + "].description", InvalidFieldsMessages.EMPTYFIELD);
      }
    } else {
      this.addMessage(baseAction.getText("programImpact.action.description.required", params));
      baseAction.getInvalidFields().put("input-impacts[" + i + "].description", InvalidFieldsMessages.EMPTYFIELD);
    }

    if (researchImpact.getShortName() != null) {
      if (!this.isValidString(researchImpact.getShortName()) && researchImpact.getShortName().length() >= 30) {
        this.addMessage(baseAction.getText("programImpact.action.shortName.required", params));
        baseAction.getInvalidFields().put("input-impacts[" + i + "].shortName", InvalidFieldsMessages.EMPTYFIELD);
      }
    } else {
      this.addMessage(baseAction.getText("programImpact.action.shortName.required", params));
      baseAction.getInvalidFields().put("input-impacts[" + i + "].shortName", InvalidFieldsMessages.EMPTYFIELD);
    }

    if (researchImpact.getObjectiveValue() != null) {
      if (researchImpact.getObjectiveValue().length() < 1) {
        this.addMessage(baseAction.getText("programImpact.action.objectiveValue.empty", params));
        baseAction.getInvalidFields().put("input-impacts[" + i + "].objectiveValue", InvalidFieldsMessages.CHECKBOX);
      }
    } else {
      this.addMessage(baseAction.getText("programImpact.action.objectiveValue.empty", params));
      baseAction.getInvalidFields().put("input-impacts[" + i + "].objectiveValue", InvalidFieldsMessages.CHECKBOX);
    }


    if (researchImpact.getBeneficiaries() != null) {
      if (researchImpact.getBeneficiaries().size() == 0) {
        this.addMessage(baseAction.getText("programImpact.action.beneficiary", params));
        baseAction.getInvalidFields().put("list-impacts[" + i + "].beneficiaries",
          baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"beneficiaries"}));
      } else {
        for (int j = 0; j < researchImpact.getBeneficiaries().size(); j++) {
          ResearchImpactBeneficiary beneficiary = researchImpact.getBeneficiaries().get(j);
          this.validateBeneficiaries(baseAction, beneficiary, i, j);
        }
      }
    } else {
      this.addMessage(baseAction.getText("programImpact.action.beneficiary", params));
      baseAction.getInvalidFields().put("list-impacts[" + i + "].beneficiaries",
        baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"beneficiaries"}));
    }
  }

}
