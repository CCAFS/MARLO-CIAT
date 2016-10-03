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

package org.cgiar.ccafs.marlo.action;

import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.Crp;
import org.cgiar.ccafs.marlo.data.service.IAuditLogService;
import org.cgiar.ccafs.marlo.data.service.ICRPService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.ibm.icu.util.Calendar;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 * @author Christian Garcia - CIAT/CCAFS
 */
@SuppressWarnings("unused")
public class OutcomesAction extends BaseAction {

  private static final long serialVersionUID = -2803122256728476290L;


  public static void printMap(Map<String, Integer> map) {
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      System.out.println("[Key] : " + entry.getKey() + " [Value] : " + entry.getValue());
    }
  }


  // private CrpMilestoneManager crpMilestoneManager;


  private long crpProgramID;


  private String transaction;
  // private CrpProgramManager crpProgramManager;
  // private CrpProgramOutcomeManager crpProgramOutcomeManager;
  // private SrfSubIdoManager srfSubIdoManager;
  private HashMap<Long, String> idoList;
  private Crp loggedCrp;
  // private List<CrpProgramOutcome> outcomes;
  // private List<CrpProgram> programs;
  // private CrpProgram selectedProgram;
  // private SrfIdoManager srfIdoManager;
  // private CrpOutcomeSubIdoManager crpOutcomeSubIdoManager;
  // private SrfTargetUnitManager srfTargetUnitManager;
  private HashMap<Long, String> targetUnitList;
  // private OutcomeValidator validator;
  // private CrpAssumptionManager crpAssumptionManager;
  private ICRPService crpManager;
  private IUserService userManager;
  // private List<SrfIdo> srfIdos;
  private List<Integer> milestoneYears;
  private IAuditLogService auditLogManager;


  @Inject
  public OutcomesAction(APConfig config, ICRPService crpManager, IUserService userManager,
    IAuditLogService auditLogManager) {
    super(config);
    this.crpManager = crpManager;
    this.userManager = userManager;
    this.auditLogManager = auditLogManager;
  }

  // @Inject
  // public OutcomesAction(APConfig config, SrfTargetUnitManager srfTargetUnitManager, SrfIdoManager srfIdoManager,
  // CrpProgramOutcomeManager crpProgramOutcomeManager, CrpMilestoneManager crpMilestoneManager,
  // CrpProgramManager crpProgramManager, OutcomeValidator validator, CrpOutcomeSubIdoManager crpOutcomeSubIdoManager,
  // CrpAssumptionManager crpAssumptionManager, CrpManager crpManager, UserManager userManager,
  // AuditLogManager auditLogManager, SrfSubIdoManager srfSubIdoManager) {
  // super(config);
  // this.srfTargetUnitManager = srfTargetUnitManager;
  // this.srfIdoManager = srfIdoManager;
  // this.crpProgramOutcomeManager = crpProgramOutcomeManager;
  // this.crpMilestoneManager = crpMilestoneManager;
  // this.crpProgramManager = crpProgramManager;
  // this.validator = validator;
  // this.crpOutcomeSubIdoManager = crpOutcomeSubIdoManager;
  // this.crpManager = crpManager;
  // this.userManager = userManager;
  // this.crpAssumptionManager = crpAssumptionManager;
  // this.auditLogManager = auditLogManager;
  // this.srfSubIdoManager = srfSubIdoManager;
  // }


  @Override
  public String cancel() {

    Path path = this.getAutoSaveFilePath();

    if (path.toFile().exists()) {
      path.toFile().delete();
    }

    this.setDraft(false);
    Collection<String> messages = this.getActionMessages();
    if (!messages.isEmpty()) {
      String validationMessage = messages.iterator().next();
      this.setActionMessages(null);
      this.addActionWarning(this.getText("cancel.autoSave") + validationMessage);
    } else {
      this.addActionMessage(this.getText("cancel.autoSave"));
    }
    messages = this.getActionMessages();

    return SUCCESS;
  }

  private Path getAutoSaveFilePath() {
    // String composedClassName = selectedProgram.getClass().getSimpleName();
    // String actionFile = this.getActionName().replace("/", "_");
    // String autoSaveFile = selectedProgram.getId() + "_" + composedClassName + "_" + actionFile + ".json";

    return Paths.get(config.getAutoSaveFolder());
  }

  public long getCrpProgramID() {
    return crpProgramID;
  }

  public HashMap<Long, String> getIdoList() {
    return idoList;
  }


  public Crp getLoggedCrp() {
    return loggedCrp;
  }


  public List<Integer> getMilestoneYears() {
    return milestoneYears;
  }


  public HashMap<Long, String> getTargetUnitList() {
    return targetUnitList;
  }


  public List<Integer> getTargetYears() {
    List<Integer> targetYears = new ArrayList<>();

    Date date = new Date();
    Calendar calendarStart = Calendar.getInstance();
    calendarStart.setTime(date);

    Calendar calendarEnd = Calendar.getInstance();
    calendarEnd.set(Calendar.YEAR, APConstants.END_YEAR);

    while (calendarStart.get(Calendar.YEAR) <= calendarEnd.get(Calendar.YEAR)) {
      // Adding the year to the list.
      targetYears.add(calendarStart.get(Calendar.YEAR));
      // Adding a year (365 days) to the start date.
      calendarStart.add(Calendar.YEAR, 1);
    }

    return targetYears;
  }

  public String getTransaction() {
    return transaction;
  }


  @Override
  public void prepare() throws Exception {


    // IAuditLog ia = auditLogManager.getHistory(4);
    loggedCrp = (Crp) this.getSession().get(APConstants.SESSION_CRP);
    // outcomes = new ArrayList<CrpProgramOutcome>();
    loggedCrp = crpManager.getCrpById(loggedCrp.getId());
    targetUnitList = new HashMap<>();


  }

  @Override
  public String save() {


    return SUCCESS;


  }

  public void setCrpProgramID(long crpProgramID) {
    this.crpProgramID = crpProgramID;
  }

  public void setIdoList(HashMap<Long, String> idoList) {
    this.idoList = idoList;
  }


  public void setLoggedCrp(Crp loggedCrp) {
    this.loggedCrp = loggedCrp;
  }


  public void setMilestoneYears(List<Integer> milestoneYears) {
    this.milestoneYears = milestoneYears;
  }


  public void setTargetUnitList(HashMap<Long, String> targetUnitList) {
    this.targetUnitList = targetUnitList;
  }

  public void setTransaction(String transactionId) {
    this.transaction = transactionId;
  }


  private HashMap<Long, String> sortByComparator(HashMap<Long, String> unsortMap) {

    // Convert Map to List
    List<HashMap.Entry<Long, String>> list = new LinkedList<HashMap.Entry<Long, String>>(unsortMap.entrySet());

    // Sort list with comparator, to compare the Map values
    Collections.sort(list, new Comparator<HashMap.Entry<Long, String>>() {

      @Override
      public int compare(HashMap.Entry<Long, String> o1, HashMap.Entry<Long, String> o2) {
        return (o1.getValue().toLowerCase().trim()).compareTo(o2.getValue().toLowerCase().trim());
      }
    });

    // Convert sorted map back to a Map
    HashMap<Long, String> sortedMap = new LinkedHashMap<Long, String>();
    for (Iterator<HashMap.Entry<Long, String>> it = list.iterator(); it.hasNext();) {
      HashMap.Entry<Long, String> entry = it.next();
      sortedMap.put(entry.getKey(), entry.getValue());
    }
    return sortedMap;
  }

  @Override
  public void validate() {
    // if (save) {
    // validator.validate(this, outcomes, selectedProgram, true);
    // }
  }

}