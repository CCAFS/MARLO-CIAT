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
  public static final String RESEARCH_AREA_FULL_PRIVILEGES = "center:{0}:area:{1}:*";
  public static final String RESEARCH_PROGRAM_FULL_PRIVILEGES = "center:{0}:area:{1}:program:{2}:*";
  public static final String RESEARCH_AREA_BASE_PERMISSION = "center:{0}:area:{1}";
  public static final String RESEARCH_PROGRAM_BASE_PERMISSION = "center:{0}:area:{1}:program:{2}";

}
