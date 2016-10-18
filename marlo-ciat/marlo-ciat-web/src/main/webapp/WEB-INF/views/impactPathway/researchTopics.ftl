[#ftl]
[#assign title = "Impact Pathway - Research Topics" /]
[#assign currentSectionString = "program-${actionName?replace('/','-')}-${programID}" /]
[#assign pageLibs = ["cytoscape","cytoscape-panzoom","select2"] /]
[#assign customJS = ["${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/impactPathway/outcomes.js"] /]
[#assign customCSS = [ "${baseUrl}/css/impactPathway/clusterActivities.css" ] /]
[#assign currentSection = "impactPathway" /]
[#assign currentStage = "clusterActivities" /]
[#assign editable = true]
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
        
        [#-- Research Areas --]
        <ul id="liaisonInstitutions" class="horizontalSubMenu text-left">
          [#list researchAreas as areas]
            [#assign isActive = (areas.id == areaID)/]
            
            <li class="${isActive?string('active','')}">
              <a href="[@s.url][@s.param name ="areaID"]${areas.id}[/@s.param][@s.param name ="edit"]true[/@s.param][/@s.url]">${areas.acronym}</a>
              
              <ul>
                [#list areas.researchPrograms as programs]                
                <li>${programs.name}</li>
                [/#list]
              </ul>           
            </li>
          [/#list]
        </ul>
                
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
          [#-- Add Outcome Button --]
          [#if editable]
            <div class="addOutcome bigAddButton text-center"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>[@s.text name="form.buttons.addOutcome"/]</div>
          [/#if]
        
          [#-- Section Buttons--]
          <div class="buttons">
            <div class="buttons-content">
              [#if editable]
                [#-- Save Button --]
                [@s.submit type="button" name="save" cssClass="button-save"]<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span> [@s.text name="form.buttons.save" /][/@s.submit]
              [/#if]
            </div>
          </div>
        
        
        [/@s.form]
      </div>
    </div>
    [#else]
     <p class="text-center borderBox inf">Program impacts are not available in for the selected research area</p>
    [/#if]
  </div>
</section>
[#-- Outcome Template --]
[@topicMacro element={} name="" index=0 template=true /]

[#include "/WEB-INF/global/pages/footer.ftl" /]

[#-- MACROS --]
[#macro topicMacro element name index template=false]
  <div id="researchTopic-${template?string('template','')}" class="borderBox researchTopic col-md-12" style="display:${template?string('none','block')}">
    [#-- Remove Button --]
    <div class=" removeElement removeCountry" title="Remove Country"></div>
    
    <div class="leftHead">
      <span class="index">${index+1}</span>
      <span class="elementId">Research Topic</span>
    </div>
    <br />
    
    [#-- Program Acronym & Name --]
    <div class="form-group"> 
      <div class="row">
        <div class="col-sm-12">[@customForm.input name="${name}[${index}].researchTopic" type="text"  i18nkey="research topic name" className="researchTopicInput" required=true editable=editable /]</div>
      </div>
    </div>

  </div>  
[/#macro]


