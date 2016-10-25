[#ftl]
[#assign title = "Impact Pathway - Program Impacts" /]
[#assign currentSectionString = "program-${actionName?replace('/','-')}-${programID}" /]
[#assign pageLibs = ["select2"] /]
[#assign customJS = ["${baseUrl}/js/impactPathway/programSubmit.js", "${baseUrl}/js/impactPathway/programImpact.js"] /]
[#assign currentSection = "impactPathway" /]
[#assign currentStage = "programImpacts" /]

[#assign breadCrumb = [
  {"label":"impactPathway", "nameSpace":"", "action":"programimpacts"}
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
          [#if programImpacts?has_content]
            [#list programImpacts as impact]
              [@programImpactMacro element={} name="" index=impact_index /]
            [/#list]
          [#else]
            [@programImpactMacro element={} name="" index=0 /]
          [/#if]
          </div>
          
          [#-- Add Impact Button --]
          [#if editable]
            <div class="addImpact bigAddButton text-center"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>[@s.text name="form.buttons.addImpact"/]</div>
          [/#if]
          
        [/@s.form]
      </div>
    </div>
  </div>
</section>

[#-- Templates --]
[@programImpactMacro element={} name="" index=-1 template=true /]

[#include "/WEB-INF/global/pages/footer.ftl" /]

[#macro programImpactMacro element name index template=false]
  <div id="programImpact-${template?string('template','')}" class="borderBox programImpact" style="display:${template?string('none','block')}">
    [#local customName = "${name}[${index}]" /]
    [#-- Remove Button --]
    [#if editable]
      <div class="removeElement removeProgramImpact" title="Remove program impact"></div>
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
        <div class="col-md-12">[@customForm.textArea name="${customName}.name" i18nkey="programImpact.name" className="" required=true editable=editable /]</div>
        <div class="col-md-4">[@customForm.input name="${customName}.targetYear" i18nkey="programImpact.targetYear" className="" required=true editable=editable /]</div>
      </div>
    </div>
    
    [#-- Startegic Objectives --]
    <div class="form-group">
      <h5>[@customForm.text name="programImpact.objectives" readText=!editable /]:[@customForm.req required=editable /]</h5>
      [#if editable ]
        [@s.fielderror cssClass="fieldError" fieldName="${customName}.objectives"/]
        [@s.checkboxlist name="${customName}.objectives" list="researchObjectives" listKey="id" listValue="objective" cssClass="checkboxInput"  value="objectivesIds" /]
      [#else]
        <input type="hidden" name="${customName}.strategicObjectives" value="${(element.strategicObjectives)!}"/>
        [#if element.strategicObjectives?has_content]
          [#list element.strategicObjectives as element]<p class="checked">${element.objective}</p>[/#list]
        [/#if]
      [/#if]
    </div>
    
    [#-- Beneficiaries--]
    <div class="form-group">
      
    </div>
    
    
  </div>
[/#macro]
