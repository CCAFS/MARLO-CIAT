[#ftl]
[#assign title = "Project Deliverable" /]
[#assign currentSectionString = "project-${actionName?replace('/','-')}-${deliverableID}" /]
[#assign pageLibs = ["select2","jsUri"] /]
[#assign customJS = ["${baseUrl}/js/global/fieldsValidation.js","${baseUrl}/js/global/usersManagement.js","${baseUrl}/js/monitoring/deliverable/projectDeliverable.js","${baseUrl}/js/global/autoSave.js"] /]
[#assign customCSS = ["${baseUrl}/css/deliverable/projectDeliverable.css"] /]
[#assign currentSection = "projects" /]
[#assign currentStage = "deliverables" /]


[#assign breadCrumb = [
  {"label":"projectsList", "nameSpace":"/monitoring", "action":"${(centerSession)!}/projectList"},
  {"label":"deliverables", "nameSpace":"/monitoring", "action":"${(centerSession)!}/deliverableList"}
] /]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

<div class="container helpText viewMore-block">
  <div class="helpMessage infoText">
    [#-- <div  class="removeHelp"><span class="glyphicon glyphicon-remove"></span></div> --]
    <p class="col-md-10"> [@s.text name="projectDescription.help" /] </p>
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
        [#-- Projects data information --]
        [#include "/WEB-INF/views/monitoring/project/dataInfo-projects.ftl" /]
        <br />
      
        [@s.form action=actionName method="POST" enctype="multipart/form-data" cssClass=""]
          
          [#-- Back --]
            <small class="pull-right">
              <a href="[@s.url action='${centerSession}/deliverableList'][@s.param name="projectID" value=project.id /][/@s.url]">
                <span class="glyphicon glyphicon-circle-arrow-left"></span> Back to the deliverables list
              </a>
            </small>  
          <h3 class="headTitle">[@s.text name="Key deliverable information" /]</h3>
          <div id="projectDeliverable" class="borderBox">
            <div class="form-group row">  
            [#-- Deliverable title --]
              <div class="col-md-12">
                [@customForm.input name="deliverable.name" i18nkey="Title" type="text" disabled=!editable  required=true editable=editable /]
              </div>  
            </div>
            <div class="form-group row">
              [#-- deliverable tpye --]
              <div class="col-md-12">
                [@customForm.select name="deliverable.deliverableType.id"  i18nkey="Type" listName="deliverableTypes" keyFieldName="id"  displayFieldName="name" className="deliverableTypeSelect" value="deliverable.deliverableType.id" /]
              </div>  
            </div>
            <div class="form-group row">  
              [#-- Start Date --]
              <div class="col-md-6">
                [@customForm.input name="deliverable.startDate" i18nkey="Start date" type="text" disabled=!editable  required=true editable=editable /]
              </div> 
              [#-- End Date --]
              <div class="col-md-6">
                [@customForm.input name="deliverable.endDate" i18nkey="End date" type="text" disabled=!editable required=false editable=editable /]
              </div>
          </div>
          <label for="">Supporting document(s):<span class="red">*</span></label>
          <div class="borderBox documentList" listname="deliverable.documents">
            [#if deliverable.documents?has_content]
              [#list deliverable.documents as document]
                [@documentMacro element=document name="deliverable.documents"  index=document_index isTemplate=false /]
              [/#list]
            [/#if]
            <p class="text-center inf" style="display:${(deliverable.documents?has_content)?string('none','block')}">[@s.text name="There are not document(s) added yet." /]</p>
          </div>
          <div class="text-right">
            <div class="button-green addDocument"><span class="glyphicon glyphicon-plus-sign"></span>[@s.text name="Add a support document" /]</div>
          </div>
        </div>
      [#-- Section Buttons & hidden inputs--]
          [#include "/WEB-INF/views/monitoring/deliverable/buttons-deliverable.ftl" /]
             
          [/@s.form] 
      
    </div>  
</section>


[@documentMacro element={} name="deliverable.documents"  index=-1 isTemplate=true /]
  
[#include "/WEB-INF/global/pages/footer.ftl"]


[#macro documentMacro element name index=-1 isTemplate=false]
  [#assign documentCustomName = "${name}[${index}]" /]
  <div id="document-${isTemplate?string('template',(element.id)!)}" class="documents form-group row"  style="display:${isTemplate?string('none','block')}">
    
    <input class="id" type="hidden" name="${documentCustomName}.id" value="${(element.id)!-1}" />
    <div class="col-md-12">
    [#if editable]<div class="removeDocument removeIcon" title="Remove document"></div>[/#if] 
      [@customForm.input name="${documentCustomName}.link" i18nkey="Link" type="text" className="link" disabled=!editable required=true editable=editable /]
    </div>
  </div>
[/#macro]
