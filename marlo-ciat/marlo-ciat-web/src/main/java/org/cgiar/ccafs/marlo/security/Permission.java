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

package org.cgiar.ccafs.marlo.security;

/**
 * This class have the statics texts of the names and paths of the privileges
 * that have a user.
 * 
 * @author Hernán David Carvajal
 * @author Hermes Jiménez - CIAT/CCAFS
 *         Represents the User of the system.
 *         Modified by @author nmatovu last on Sep 29, 2016
 */
public class Permission {

  public static final String FULL_PRIVILEGES = "*"; // path full privileges
  public static final String CRP_ADMIN_VISIBLE_PRIVILEGES = "admin:canAcess"; // path
  // Full privileges
  public static final String CRP_ADMIN_EDIT_PRIVILEGES = "admin:*"; // path
  // Full privileges
  public static final String CRP_ADMIN_BASE_PERMISSION = "admin";
  public static final String IMPACT_PATHWAY_BASE_PERMISSION = "impactPathway:{0}";
  public static final String IMPACT_PATHWAY_EDIT_PRIVILEGES = "impactPathway:{0}:*";
  public static final String IMPACT_PATHWAY_VISIBLE_PRIVILEGES = "impactPathway:canAcess";
  public static final String PROJECT_DESCRIPTION_BASE_PERMISSION = "project:{0}:description";
  public static final String PROJECT_LIST_BASE_PERMISSION = "project:projectsList";
  public static final String PROJECT_CONTRIBRUTIONCRP_BASE_PERMISSION = "project:{0}:contributionCrp";
  public static final String PROJECT_DESCRIPTION_PERMISSION = "project:{0}:description:*";
  public static final String PROJECT_PARTNER_BASE_PERMISSION = "project:{0}:partners";
  public static final String PROJECT_BUDGET_BASE_PERMISSION = "project:{0}:budget";
  public static final String PROJECT_BUDGET_CLUSTER_BASE_PERMISSION = "project:{0}:budgetByCluster";
  public static final String PROJECT_MANAGE_BASE_PERMISSION = "project:{0}:manage";
  public static final String PROJECT_SUBMISSION_PERMISSION = "project:{0}:manage:submitProject";
  public static final String PROJECT_LOCATION_BASE_PERMISSION = "project:{0}:location";

  public static final String PROJECT__PERMISSION = "project:{0}:{1}:canEdit";

  public static final String PROJECT__SWITCH = "project:{0}:projectSwitch";
  public static final String PROJECT_CONTRIBRUTIONCRP_EDIT_PERMISSION = "project:{0}:contributionCrp:canEdit";
  public static final String PROJECT_DELIVERABLE_LIST_EDIT_PERMISSION = "project:{0}:deliverablesList:canEdit";
  public static final String PROJECT_DELIVERABLE_LIST_ADD_PERMISSION = "project:{0}:deliverablesList:addDeliverable";
  public static final String PROJECT_DELIVERABLE_LIST_REMOVE_PERMISSION =
    "project:{0}:deliverablesList:removeOldDeliverables";
  public static final String PROJECT_DELIVERABLE_BASE_PERMISSION = "project:{0}:deliverable";
  public static final String PROJECT_DELIVERABLE_EDIT_PERMISSION = "project:{0}:deliverable:canEdit";
  public static final String PROJECT_ACTIVITIES_BASE_PERMISSION = "project:{0}:activities";
  public static final String PROJECT_W3_COFUNDED_BASE_PERMISSION = "projectCofunded:{0}:canEdit";

  // Create Projects Permissions
  public static final String PROJECT_CORE_ADD = "project:projectsList:addCoreProject";
  public static final String PROJECT_BILATERAL_ADD = "project:projectsList:addBilateralProject";

}
