[#ftl]
[#assign title = "Impact Pathway - Program Impacts" /]
[#assign currentSectionString = "program-${actionName?replace('/','-')}-${programID}" /]
[#assign pageLibs = ["select2"] /]
[#assign customJS = ["${baseUrl}/js/impactPathway/programSubmit.js", "${baseUrl}/js/impactPathway/programImpact.js", "${baseUrl}/js/global/fieldsValidation.js"] /]
[#assign currentSection = "impactPathway" /]
[#assign currentStage = "programImpacts" /]

[#assign breadCrumb = [
  {"label":"impactPathway", "nameSpace":"", "action":"programimpacts"},
  {"label":"programImpacts", "nameSpace":"", "action":"programimpacts"}
]/]
[#assign leadersName = "leaders"/]
[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utils /]
[#--  marlo cluster of activities--]
<section class="marlo-content">
  <div class="container"> 
    
    <div class="row">
      <div class="col-md-3">
        [#include "/WEB-INF/views/impactPathway/menu-impactPathway.ftl" /]
      </div>
      <div class="col-md-9">
        [#-- Section Messages --]
        [#include "/WEB-INF/views/impactPathway/messages-impactPathway.ftl" /]
        [#-- Impact pathway sub menu --]
        [#include "/WEB-INF/views/impactPathway/submenu-impactPathway.ftl" /]
        
        [@s.form action=actionName enctype="multipart/form-data" ]
          
          <div class="elements-list">
          [#if researchImpacts?has_content]
            [#list researchImpacts as impact]
              [@programImpactMacro element=impact name="researchImpacts" index=impact_index /]
            [/#list]
          [#else]
            [@programImpactMacro element={} name="researchImpacts" index=0 /]
          [/#if]
          </div>
          
          [#-- Add Impact Button --]
          [#if editable]
            <div class="addImpact bigAddButton text-center"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>[@s.text name="form.buttons.addImpact"/]</div>
          [/#if]
          
          [#-- Section Buttons--]
          [#include "/WEB-INF/views/impactPathway/buttons-impactPathway.ftl" /]
          
        [/@s.form]
      </div>
    </div>
  </div>
</section>

[#-- Templates --]
[@programImpactMacro element={} name="researchImpacts" index=-1 template=true /]

[@beneficiaryMacro beneficiary={} name="researchImpacts[-1].beneficiaries" index=-1 template=true/]

[#include "/WEB-INF/global/pages/footer.ftl" /]

[#macro programImpactMacro element name index template=false]
  <div id="programImpact-${template?string('template','')}" class="borderBox programImpact" style="display:${template?string('none','block')}">
    [#local customName = "${name}[${index}]" /]
    [#-- Remove Button --]
    [#if editable]
    [#if element.id?has_content]
      [#if template || action.canBeDeleted(element.id, element.class.name)!false]
        <span class="glyphicon glyphicon-remove pull-right removeProgramImpact" style="color:#FF0000" aria-hidden="true"></span>
      [#else]
        <span class="glyphicon glyphicon-remove pull-right" style="color:#ccc" aria-hidden="true" title="Can not be deleted"></span>
      [/#if]
    [#else]
        <span class="glyphicon glyphicon-remove pull-right removeProgramImpact" style="color:#FF0000" aria-hidden="true"></span>
    [/#if]  
    [/#if]
    
    <div class="leftHead">
      <span class="index">${index+1}</span>
      <span class="elementId">[@s.text name="programImpact.programImpact" /]</span>
    </div>
    <br />
    
    <input type="hidden" name="${customName}.id" value="${(element.id)!}"/>
    
    [#-- Program Impact & Target Year--]
    <div class="form-group"> 
      
      <div class="row">
        <div class="col-md-12">[@customForm.textArea name="${customName}.description" i18nkey="programImpact.name" className="" required=true editable=editable /]</div>
        <div class="col-md-4">
        [#if editable]
          [@customForm.select name="${customName}.targetYear" label=""  i18nkey="programImpact.targetYear" listName="allYears"   multiple=false required=true  className="yearExpected" editable=editable/]
        [#else]
          <div class="select">
            <label for=""> [@s.text name="programImpact.targetYear" /]:  </label>
            <div class="selectList"><p> [#if element.targetYear?has_content][#if element.targetYear == -1] Not Selected [#else]${(element.targetYear)!'none'}[/#if][#else]Not Selected[/#if] </p></div> 
          </div>
        [/#if]
        </div>     
      </div>
    </div>
    
    [#-- Startegic Objectives --]
    <div class="form-group relVal" >
      <h5>[@customForm.text name="programImpact.objectiveValue" readText=!editable /]:[@customForm.req required=editable /]</h5>
      [#if editable ]
        [@s.fielderror cssClass="fieldError" fieldName="${customName}.objectiveValue"/]
        [@s.checkboxlist name="${customName}.objectiveValue" list="researchObjectives" listKey="id" listValue="objective" cssClass="checkboxInput"  value="${customName}.objectivesIds" /]
      [#else]
        <input type="hidden" name="${customName}.objectiveValue" value="${(element.objectiveValue)!}"/>
        [#if element.objectives?has_content]
          [#list element.objectives as element]<p class="checked">${element.objective}</p>[/#list]
        [/#if]
      [/#if]
    </div>
    
    [#-- Beneficiaries--]
    <h5 class="sectionSubTitle">[@s.text name="Intended beneficiaries: "/]</h5>
    <div class="alert alert-info" role="alert"><span class="glyphicon glyphicon-info-sign"></span> please list (as much as you know now) who will benefit from your Program by Target year.</div>
    <div class="form-group simpleBox beneficiaries-list">
      [#if element.beneficiaries?has_content]
        [#list element.beneficiaries as beneficiary]
          [@beneficiaryMacro beneficiary=beneficiary name="${customName}.beneficiaries" index=beneficiary_index /]
        [/#list]
      [#else]
        <p class="message text-center">[@s.text name="There are not beneficiaries added yet.."/]</p>
      [/#if]
    </div>
    <div class="text-right">
      <div class="addBeneficiary button-blue text-right"><span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> [@s.text name="Add beneficiary"/]</div>
    </div>
    
    
  </div>
[/#macro]

[#macro beneficiaryMacro beneficiary name index template=false]
  [#assign beneficiaryCustomName = "${name}[${index}]" /]
  <div id="beneficiary-${template?string('template', index)}" class="beneficiary borderBox form-group" style="position:relative; display:${template?string('none','block')}">
    [#-- Remove Button --]
    [#if editable]
    <div class="removeBeneficiary removeIcon" title="Remove assumption"></div>
    [/#if]
    <input type="hidden" class="beneficiaryId" name="${beneficiaryCustomName}.id" value="${(beneficiary.id)!}"/>
  
  [#-- Type select --]
  <div class="col-md-4">
  [@customForm.select name="${beneficiaryCustomName}.beneficiary.beneficiaryType.id" label=""  i18nkey="Type" listName="beneficiaryTypes" keyFieldName="id"  displayFieldName="name"  multiple=false required=true  className="typeSelect form-control input-sm " editable=editable/]
  </div>   

  [#-- Focus select --]
  <div class="col-md-4">
  [@customForm.select name="${beneficiaryCustomName}.beneficiary.id" label=""  i18nkey="Focus" listName="${beneficiaryCustomName}.beneficiary.beneficiaryType.beneficiaries" keyFieldName="id"  displayFieldName="name"  multiple=false required=true  className="focusSelect form-control input-sm " editable=editable/]
  </div>
  
  [#-- Region select --]
  <div class="col-md-4">
  [@customForm.select name="${beneficiaryCustomName}.researchRegion.id" label=""  i18nkey="Region" listName="regions" keyFieldName="id"  displayFieldName="name"  multiple=false required=true  className="regionSelect form-control input-sm" editable=editable/]
  </div>
  
  <div class="clearfix"></div>
  </div>
[/#macro]
