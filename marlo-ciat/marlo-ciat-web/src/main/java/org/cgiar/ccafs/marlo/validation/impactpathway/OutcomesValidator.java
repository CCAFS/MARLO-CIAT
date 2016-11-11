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
import org.cgiar.ccafs.marlo.data.model.ResearchMilestone;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
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
public class OutcomesValidator extends BaseValidator {

  @Inject
  public OutcomesValidator() {
    // TODO Auto-generated constructor stub
  }

  public void validate(BaseAction baseAction, ResearchOutcome outcome, ResearchProgram selectedProgram,
    boolean saving) {

    baseAction.setInvalidFields(new HashMap<>());

    // TODO validator autosave file

    if (!baseAction.getFieldErrors().isEmpty()) {
      baseAction.addActionError(baseAction.getText("saving.fields.required"));
    }

    this.validateOutcome(baseAction, outcome);

    this.saveMissingFields(selectedProgram, outcome, "outcomes");

  }

  public void validateMilestones(BaseAction baseAction, ResearchMilestone milestone, int i) {
    List<String> params = new ArrayList<String>();
    params.add(String.valueOf(i + 1));

    if (!this.isValidString(milestone.getTitle()) && this.wordCount(milestone.getTitle()) <= 50) {
      this.addMessage(baseAction.getText("outcome.milestone.action.statement.required"));
      baseAction.getInvalidFields().put("input-outcome.milestones[" + i + "].title", InvalidFieldsMessages.EMPTYFIELD);
    }

    if (milestone.getTargetYear() == -1) {
      this.addMessage(baseAction.getText("outcome.milestone.action.targetYear.required"));
      baseAction.getInvalidFields().put("input-outcome.milestones[" + i + "].targetYear",
        InvalidFieldsMessages.EMPTYFIELD);
    }

    if (!this.isValidNumber(String.valueOf(milestone.getValue()))) {
      if (milestone.getTargetUnit().getId() != -1) {
        this.addMessage(baseAction.getText("outcome.milestone.action.value.required"));
        baseAction.getInvalidFields().put("input-outcome.milestones[" + i + "].value",
          InvalidFieldsMessages.EMPTYFIELD);
      }
    }

  }

  public void validateOutcome(BaseAction baseAction, ResearchOutcome outcome) {

    if (outcome.getResearchImpact().getId() == -1) {
      this.addMessage(baseAction.getText("outcome.action.impactPathway.required"));
      baseAction.getInvalidFields().put("input-outcome.researchImpact", InvalidFieldsMessages.EMPTYFIELD);
    }

    if (!this.isValidString(outcome.getDescription()) && this.wordCount(outcome.getDescription()) <= 100) {
      this.addMessage(baseAction.getText("outcome.action.statement.required"));
      baseAction.getInvalidFields().put("input-outcome.description", InvalidFieldsMessages.EMPTYFIELD);
    }

    if (outcome.getTargetYear() == -1) {
      this.addMessage(baseAction.getText("outcome.action.targetYear.required"));
      baseAction.getInvalidFields().put("input-outcome.targetYear", InvalidFieldsMessages.EMPTYFIELD);
    }

    if (!this.isValidNumber(String.valueOf(outcome.getValue()))) {
      if (outcome.getTargetUnit().getId() != -1) {
        this.addMessage(baseAction.getText("outcome.action.value.required"));
        baseAction.getInvalidFields().put("input-outcome.value", InvalidFieldsMessages.EMPTYFIELD);
      }
    }

    if (outcome.getResearchMilestones() == null || outcome.getMilestones().isEmpty()) {
      this.addMessage("outcome.action.milestones.required");
      baseAction.getInvalidFields().put("list-outcome.milestones",
        baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"Milestones"}));
    } else {
      for (int i = 0; i < outcome.getMilestones().size(); i++) {
        this.validateMilestones(baseAction, outcome.getMilestones().get(i), i);
      }

    }


  }

}
