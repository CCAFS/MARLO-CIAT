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
import org.cgiar.ccafs.marlo.data.model.Deliverable;
import org.cgiar.ccafs.marlo.data.model.DeliverableDocument;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.utils.InvalidFieldsMessages;
import org.cgiar.ccafs.marlo.validation.BaseValidator;

import java.util.HashMap;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class DeliverableValidator extends BaseValidator {

  public void validate(BaseAction baseAction, Deliverable deliverable, Project project, ResearchProgram selectedProgram,
    boolean saving) {

    baseAction.setInvalidFields(new HashMap<>());

    // TODO Autosave Draft validator.

    if (!baseAction.getFieldErrors().isEmpty()) {
      baseAction.addActionError(baseAction.getText("saving.fields.required"));
    }

    this.validateDeliverable(baseAction, deliverable);

    this.saveMissingFields(deliverable, project, "deliverableList");

  }


  public void validateDeliverable(BaseAction baseAction, Deliverable deliverable) {

    if (!this.isValidString(deliverable.getName()) && this.wordCount(deliverable.getName()) <= 50) {
      this.addMessage(baseAction.getText("deliverable.action.deliverablesName"));
      baseAction.getInvalidFields().put("input-deliverable.name", InvalidFieldsMessages.EMPTYFIELD);
    }

    if (deliverable.getStartDate() == null) {
      this.addMessage(baseAction.getText("deliverable.action.deliverablesStartDate"));
      baseAction.getInvalidFields().put("input-deliverable.startDate", InvalidFieldsMessages.EMPTYFIELD);
    }
    if (deliverable.getEndDate() == null) {
      this.addMessage(baseAction.getText("deliverable.action.deliverablesEndDate"));
      baseAction.getInvalidFields().put("input-deliverable.endDate", InvalidFieldsMessages.EMPTYFIELD);
    }

    if (deliverable.getDeliverableType().getId() == null || deliverable.getDeliverableType().getId() == -1) {
      this.addMessage(baseAction.getText("deliverable.action.deliverablesType"));
      baseAction.getInvalidFields().put("input-deliverable.deliverableType", InvalidFieldsMessages.EMPTYFIELD);
    }

    if (deliverable.getDocuments() == null || deliverable.getDocuments().isEmpty()) {
      this.addMessage(baseAction.getText("deliverable.action.documents"));
      baseAction.getInvalidFields().put("list-deliverable.documents",
        baseAction.getText(InvalidFieldsMessages.EMPTYLIST, new String[] {"Suporting Documents"}));
    } else {
      for (int i = 0; i < deliverable.getDocuments().size(); i++) {
        this.validateDocument(baseAction, deliverable.getDocuments().get(i), i);
      }
    }


  }

  public void validateDocument(BaseAction baseAction, DeliverableDocument document, int i) {
    if (!this.isValidString(document.getLink()) && this.wordCount(document.getLink()) <= 200) {
      this.addMessage(baseAction.getText("deliverable.action.documents.link", String.valueOf(i + 1)));
      baseAction.getInvalidFields().put("input-deliverable.documents[" + i + "].link",
        InvalidFieldsMessages.EMPTYFIELD);
    }

  }

}
