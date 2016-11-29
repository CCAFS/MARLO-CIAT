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
import org.cgiar.ccafs.marlo.data.model.ResearchImpact;
import org.cgiar.ccafs.marlo.data.model.ResearchImpactBeneficiary;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.utils.InvalidFieldsMessages;
import org.cgiar.ccafs.marlo.validation.BaseValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.inject.Inject;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ProgramImpactsValidator extends BaseValidator {

  @Inject
  public ProgramImpactsValidator() {
    // TODO Auto-generated constructor stub
  }


  public void validate(BaseAction baseAction, List<ResearchImpact> researchImpacts, ResearchProgram selectedProgram,
    boolean saving) {

    baseAction.setInvalidFields(new HashMap<>());


    // TODO validator autosave file

    if (!baseAction.getFieldErrors().isEmpty()) {
      baseAction.addActionError(baseAction.getText("saving.fields.required"));
    }

    if (researchImpacts.size() == 0) {
      this.addMissingField("researchImpacts");
      this.addMessage(baseAction.getText("programImpact.action.required"));
      baseAction.getInvalidFields().put("list-researchImpacts",
        baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"researchImpacts"}));

    }

    for (int i = 0; i < researchImpacts.size(); i++) {
      ResearchImpact researchImpact = researchImpacts.get(i);
      this.validateProgramImpact(baseAction, researchImpact, i);
    }

    this.saveMissingFields(selectedProgram, "researchImpact");
  }

  public void validateBeneficiaries(BaseAction baseAction, ResearchImpactBeneficiary impactBeneficiary, int i, int j) {

    List<String> params = new ArrayList<String>();
    params.add(String.valueOf(i + 1));
    params.add(String.valueOf(j + 1));

    if (impactBeneficiary.getResearchRegion().getId() == -1) {
      this.addMessage(baseAction.getText("programImpact.action.beneficiary.region", params));
      baseAction.getInvalidFields().put("input-researchImpacts[" + i + "].beneficiaries[" + j + "].researchRegion.id",
        InvalidFieldsMessages.EMPTYFIELD);
    }

    if (impactBeneficiary.getBeneficiary().getId() == -1) {
      this.addMessage(baseAction.getText("programImpact.action.beneficiary.focus", params));
      baseAction.getInvalidFields().put("input-researchImpacts[" + i + "].beneficiaries[" + j + "].beneficiary.id",
        InvalidFieldsMessages.EMPTYFIELD);
    }

    if (impactBeneficiary.getBeneficiary().getBeneficiaryType().getId() == -1) {
      this.addMessage(baseAction.getText("programImpact.action.beneficiary.type", params));
      baseAction.getInvalidFields().put(
        "input-researchImpacts[" + i + "].beneficiaries[" + j + "].beneficiary.beneficiaryType.id",
        InvalidFieldsMessages.EMPTYFIELD);
    }
  }

  public void validateProgramImpact(BaseAction baseAction, ResearchImpact researchImpact, int i) {
    List<String> params = new ArrayList<String>();
    params.add(String.valueOf(i + 1));

    if (!this.isValidString(researchImpact.getDescription())
      && this.wordCount(researchImpact.getDescription()) <= 150) {
      this.addMessage(baseAction.getText("programImpact.action.description.required", params));
      baseAction.getInvalidFields().put("input-researchImpacts[" + i + "].description",
        InvalidFieldsMessages.EMPTYFIELD);
    }

    if (researchImpact.getTargetYear() == -1) {
      this.addMessage(baseAction.getText("programImpact.action.year.required", params));
      baseAction.getInvalidFields().put("input-researchImpacts[" + i + "].targetYear",
        InvalidFieldsMessages.EMPTYFIELD);
    }

    if (researchImpact.getObjectiveValue().length() < 1) {
      this.addMessage(baseAction.getText("programImpact.action.objectiveValue.empty", params));
      baseAction.getInvalidFields().put("input-researchImpacts[" + i + "].objectiveValue",
        InvalidFieldsMessages.CHECKBOX);
    }

    if (researchImpact.getBeneficiaries() != null) {
      if (researchImpact.getBeneficiaries().size() == 0) {
        this.addMissingField("beneficiaries");
        this.addMessage(baseAction.getText("programImpact.action.beneficiary"));
        baseAction.getInvalidFields().put("list-researchImpacts[" + i + "].beneficiaries",
          baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"beneficiaries"}));
      } else {
        for (int j = 0; j < researchImpact.getBeneficiaries().size(); j++) {
          ResearchImpactBeneficiary beneficiary = researchImpact.getBeneficiaries().get(j);
          this.validateBeneficiaries(baseAction, beneficiary, i, j);
        }
      }
    } else {
      this.addMissingField("beneficiaries");
      this.addMessage(baseAction.getText("programImpact.action.beneficiary"));
      baseAction.getInvalidFields().put("list-researchImpacts[" + i + "].beneficiaries",
        baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"beneficiaries"}));
    }
  }

}
