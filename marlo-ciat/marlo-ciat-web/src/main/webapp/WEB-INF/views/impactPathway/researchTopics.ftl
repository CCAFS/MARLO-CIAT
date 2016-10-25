[#ftl]
[#assign title = "Impact Pathway - Research Topics" /]
[#assign currentSectionString = "program-${actionName?replace('/','-')}-${programID}" /]
[#assign pageLibs = ["cytoscape","cytoscape-panzoom","select2"] /]
[#assign customJS = ["${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/impactPathway/researchTopics.js"] /]
[#assign customCSS = [ "${baseUrl}/css/impactPathway/clusterActivities.css" ] /]
[#assign currentSection = "impactPathway" /]
[#assign currentStage = "researchTopics" /]

[#assign breadCrumb = [
  {"label":"impactPathway", "nameSpace":"", "action":"topics"}
]/]
[#assign leadersName = "leaders"/]
[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utils /]
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
        
        [@s.form action=actionName enctype="multipart/form-data" ]     
          <div class="outcomes-list" listname="outcomes">
          [#if researchTopics?has_content]
            [#list researchTopics as outcome]
              [@topicMacro element=outcome name="researchTopics" index=outcome_index /]
            [/#list]
          [#else]
            [@topicMacro element={} name="researchTopics" index=0 /]
          [/#if]          
          </div>
          <div class="clearfix"></div>
          [#-- Add Research Impact Button --]
          [#if editable]
            <div class="addResearchTopic bigAddButton text-center"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>[@s.text name="form.buttons.addResearchTopic"/]</div>
          [/#if]
        
          
          [#-- Section Buttons--]
          [#include "/WEB-INF/views/impactPathway/buttons-impactPathway.ftl" /]
          
          
        [/@s.form]
      </div>
    </div>
    [#else]
     <p class="text-center borderBox inf">Program impacts are not available in for the selected research area</p>
    [/#if]
  </div>
</section>
[#-- Outcome Template --]
[@topicMacro element={} name="researchTopics" index=-1 template=true /]

[#include "/WEB-INF/global/pages/footer.ftl" /]

[#-- MACROS --]
[#macro topicMacro element name index template=false]
  <div id="researchTopic-${template?string('template','')}" class="borderBox researchTopic col-md-12" style="display:${template?string('none','block')}">
    [#local customName = "${name}[${index}]" /]
    [#-- Remove Button --]
    [#if editable]
      <div class=" removeElement removeResearchTopic" title="Remove Research Topic"></div>
    [/#if]
    
    <div class="leftHead">
      <span class="index">${index+1}</span>
      <span class="elementId">Research Topic</span>
    </div>
    <br />
    
    <input type="hidden" name="${customName}.id" value="${(element.id)!}"/>
    
    [#-- Program Acronym & Name --]
    <div class="form-group"> 
      <div class="row">
        <div class="col-sm-12">[@customForm.input name="${customName}.researchTopic" type="text"  i18nkey="researchTopic.name" className="researchTopicInput" required=true editable=editable /]</div>
      </div>
    </div>
  </div>  
[/#macro]


