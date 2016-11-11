[#ftl]
[#assign title = "Outcomes List" /]
[#assign currentSectionString = "program-${actionName?replace('/','-')}-${programID}" /]
[#assign pageLibs = ["datatables.net", "datatables.net-bs","select2"] /]
[#assign customJS = ["${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/impactPathway/outcomeList.js", "${baseUrl}/js/global/fieldsValidation.js"] /]
[#assign customCSS = ["${baseUrl}/css/global/customDataTable.css","${baseUrl}/css/impactPathway/outcomeList.css"] /]
[#assign currentSection = "impactPathway" /]
[#assign currentStage = "outcomes" /]

[#assign breadCrumb = [
  {"label":"impactPathway", "nameSpace":"", "action":"topics"},
  {"label":"outcomesList", "nameSpace":"", "action":""}
]/]
[#assign leadersName = "leaders"/]
[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utils /]
[#import "/WEB-INF/views/impactPathway/outcomeListTemplate.ftl" as outcomesList /]
[#--  marlo cluster of activities--]
<section class="marlo-content">
  <div class="container"> 
    [#if researchAreas?has_content]
    <div class="row">
      <div class="col-md-3">
        [#include "/WEB-INF/views/impactPathway/menu-impactPathway.ftl" /]
      </div>
      <div class="col-md-9">
        [#-- Section Messages --]
        [#include "/WEB-INF/views/impactPathway/messages-impactPathway.ftl" /]
        [#-- Impact pathway sub menu --]
        [#include "/WEB-INF/views/impactPathway/submenu-impactPathway.ftl" /]
        
        [#if researchTopics?has_content]
        
        <span id="programSelected" class="hidden">${selectedProgram.id}</span>
        
        [@s.form action=actionName enctype="multipart/form-data" ]
        
        
          <div class="simpleBox col-md-12">
            <label for="">Research Topics:<span class="red">*</span></label>
            <select name="researchTopics" id="researchTopics">
              <option value="-1" >Select an option</option>
              
                [#list researchTopics as researchTopic]
                  <option value="${researchTopic.id}"[#if (selectedResearchTopic.id)?has_content && (selectedResearchTopic.id== researchTopic.id)] selected="selected"[/#if]] >${researchTopic.researchTopic}</option>
                [/#list]
              
            </select>
          </div>
          
          <div class="col-md-12">
            <h3 class="subTitle headTitle outcomeListTitle">${selectedProgram.name} - Outcomes</h3>
            <hr />
          </div>
          
          <div style="">[@outcomesList.outcomesList outcomes=outcomes canValidate=true canEdit=candit namespace="/impactPathway" defaultAction="${(centerSession)!}/outcomes"/]</div>
          <div class="text-right">
            [#if editable]
            <div class="addOutcome button-blue"><a  href="[@s.url namespace="/${currentSection}" action='${(centerSession)!}/addNewOutcome'] [@s.param name="programID"]${selectedProgram.id}[/@s.param][/@s.url]">
              <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> [@s.text name="form.buttons.addOutcome" /]
            </a></div>
            [/#if]
          </div>
          
        [/@s.form]
        [#else]
         <p class="text-center borderBox inf">Before completing this section, please add at least one Research Topic by <a href="[@s.url action='${centerSession}/researchTopics'][@s.param name="programID" value=programID /][@s.param name="edit" value="true"/][/@s.url]">clicking here</a></p> 
        [/#if]
      </div>
    </div>
    [#else]
     <p class="text-center borderBox inf">Program impacts are not available in for the selected research area</p>
    [/#if]
  </div>
</section>

[#include "/WEB-INF/global/pages/footer.ftl" /]


