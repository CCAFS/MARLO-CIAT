[#ftl]
[#assign items= [
  { 'slug': 'programImpacts',           'name': 'impactPathway.menu.hrefProgramImpacts',  'action': 'programimpacts',           'active': true  },
  { 'slug': 'researchTopics',  'name': 'impactPathway.menu.hrefResearchTopics',       'action': 'researchTopics',  'active': true },
  { 'slug': 'outcomes',           'name': 'impactPathway.menu.hrefOutcomes',  'action': 'outcomes',           'active': true  },
  { 'slug': 'outputs',  'name': 'impactPathway.menu.hrefOutputs',       'action': 'outputs',  'active': true }
]/]


[#assign submission = (action.submission)! /]
[#assign canSubmit = (action.hasPersmissionSubmit())!false /]
[#assign completed = action.isCompleteImpact(programID) /]


[#-- Menu  ${action.getImpactSectionStatus(actionName, crpProgramID)?string("","hasMissingFields")} --]
<nav id="secondaryMenu" class="">
  <p>[@s.text name="impactPathway.menu.title"/]</p>
  <div class="menuList">
  [#-- Research Areas --]
    [#list researchAreas as area]
      [#assign isActive = (area.id == areaID)/]
      <p class="${isActive?string('active','')}"><a href="[@s.url][@s.param name ="areaID"]${area.id}[/@s.param][@s.param name ="edit"]true[/@s.param][/@s.url]">[@s.text name="research.area.menu"/] ${area.acronym}</a></p>
    [/#list]
    
    [#-- Research Programs --]
    [#list researchPrograms as program]
     <p><a href="[@s.url][@s.param name ="programID"]${area.id}[/@s.param][@s.param name ="edit"]true[/@s.param][/@s.url]">[@s.text name="research.program.menu"/] ${program.acronym}</a></p>
    [/#list]
  </div>
  <ul>
    <li>
      <ul>
        [#list items as item]
          <li id="menu-${item.action}" class="[#if item.slug == currentStage]currentSection[/#if]${(item.active)?string('enabled','disabled')}">
            <a href="[@s.url action="${crpSession}/${item.action}"][@s.param name="programID" value=programID /][@s.param name="edit" value="true"/][/@s.url]" onclick="return ${item.active?string}">
              [@s.text name=item.name/]
            </a>
          </li>
        [/#list] 
      </ul>
    </li>
  </ul> 
</nav>

<div class="clearfix"></div>