[#ftl]
[#assign title = "Project Partners" /]
[#assign currentSectionString = "project-${actionName?replace('/','-')}-${projectID}" /]
[#assign pageLibs = ["select2"] /]
[#assign customJS = ["${baseUrl}/js/global/fieldsValidation.js","${baseUrl}/js/global/usersManagement.js"] /]
[#assign currentSection = "monitoring" /]
[#assign currentStage = "projectPartners" /]
[#assign editable = true /]

[#assign breadCrumb = [
  {"label":"projectsList", "nameSpace":"/monitoring", "action":"${(centerSession)!}/projectList"},
  {"label":"projectPartners", "nameSpace":"/monitoring", "action":""}
] /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#-- Search users Interface --]
[#import "/WEB-INF/global/macros/usersPopup.ftl" as usersForm/]

<div class="container helpText viewMore-block">
  <div class="helpMessage infoText">
    [#-- <div  class="removeHelp"><span class="glyphicon glyphicon-remove"></span></div> --]
    <p class="col-md-10"> [@s.text name="projectPartners.help" /] </p>
  </div> 
  <div style="display:none" class="viewMore closed"></div>
</div>
    
<section class="container">
    <div class="row">
      [#-- Project Menu --]
      <div class="col-md-3">
        [#include "/WEB-INF/views/monitoring/project/menu-projects.ftl" /]
      </div>
      [#-- Project Section Content --]
      <div class="col-md-9">
        [#-- Section Messages --]
        [#--  --include "/WEB-INF/views/projects/messages-projects.ftl" / --]
      
        [@s.form action=actionName method="POST" enctype="multipart/form-data" cssClass=""]
          
          <h3 class="headTitle">[@s.text name="Project Partners" /]</h3>  
          <div id="projectDescription" class="borderBox">
            <div class="borderBox partnerBlock">
              <h5 class="sectionSubTitle">CLAYUCA - Corporación clayuca - Colombia 
              <div class="col-md-3" style="display:inline-block; position:absolute; right:3%;">
                <label style="margin-right:8%;" for=""><input style="margin:0;" type="radio" name="partnerType" />External</label>
                <label  for=""><input style="margin:0;" type="radio" name="partnerType" />Internal</label>
              </div>
              </h5>
              <label for="">Contact person:<span class="red">*</span> </label>
              <div class="simpleBox partnerList">
                [#if project.partners?has_content]
                  [#list project.partners as partner]
                    [@evidenceMacro evidence=evidence name="outcome.monitorings[${outcome_index}].evidences" index=evidence_index /]
                  [/#list]
                [#else]
                  <p class="message text-center">[@s.text name="There are not partners associated to this project as of yet"/]</p>
                [/#if]
              </div>
            </div>
            [@customForm.select name=""  i18nkey="Select a partner" listName="deliverableTypes" keyFieldName="id"  displayFieldName="name" className="partnerSelect" value="" /]
          </div>
      </div>
      [#-- Section Buttons & hidden inputs--]
          [#include "/WEB-INF/views/monitoring/project/buttons-projects.ftl" /]
             
          [/@s.form] 
    </div>  
</section>


[@fundingSourceMacro element={} name="project.fundingSources"  index=-1 isTemplate=true /]
[@usersForm.searchUsers/]
  
[#include "/WEB-INF/global/pages/footer.ftl"]


[#macro fundingSourceMacro element name index=-1 isTemplate=false]  
  [#assign fundingSourceCustomName = "${name}[${index}]" /]
  <div id="fundingSource-${isTemplate?string('template',(element.id)!)}" class="fundingSources  borderBox row"  style="display:${isTemplate?string('none','block')}">
    [#if editable]<div class="removeFundingSource removeIcon" title="Remove funding source"></div>[/#if] 
    <input class="id" type="hidden" name="${fundingSourceCustomName}.id" value="${(element.id)!-1}" />
    <div class="col-md-4">
      [@customForm.input name="${fundingSourceCustomName}.ocsCode" i18nkey="OCS code" type="text" disabled=!editable required=false editable=editable /]
    </div>
    <div class="col-md-12">
      [@customForm.input name="${fundingSourceCustomName}.title" i18nkey="Title" type="text" disabled=!editable required=false editable=editable /]
    </div>
    <div class="col-md-4">
      [@customForm.select name="${fundingSourceCustomName}.fundingSourceType.id" label=""  i18nkey="Funding source" listName="fundingSourceTypes" keyFieldName="id"  displayFieldName="name"  multiple=false required=true header=false className="" editable=editable/]
    </div>
    <div class="col-md-8">
      [@customForm.input name="${fundingSourceCustomName}.donor" i18nkey="Donor" type="text" disabled=!editable  required=false editable=editable /]
    </div>
    <div class="clearfix"></div>
  </div>
[/#macro]
