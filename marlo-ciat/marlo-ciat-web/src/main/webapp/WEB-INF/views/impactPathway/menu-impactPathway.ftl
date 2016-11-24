[#ftl]
[#assign items= [
  { 'slug': 'programImpacts',           'name': 'impactPathway.menu.hrefProgramImpacts',  'action': 'programimpacts',           'active': true  },
  { 'slug': 'researchTopics',  'name': 'impactPathway.menu.hrefResearchTopics',       'action': 'researchTopics',  'active': true },
  { 'slug': 'outcomes',           'name': 'impactPathway.menu.hrefOutcomes',  'action': 'outcomesList',           'active': true  },
  { 'slug': 'outputs',  'name': 'impactPathway.menu.hrefOutputs',       'action': 'outputsList',  'active': true }
]/]


[#assign submission = (action.submission)! /]
[#assign canSubmit = (action.hasPersmissionSubmit())!false /]
[#-- #assign completed = action.isCompleteImpact(programID) --]


<nav id="secondaryMenu" class="">
  <p>[@s.text name="impactPathway.menu.title"/]</p>
  <ul>
    <li>
      <ul>
        [#list items as item]
          <li id="menu-${item.action}" class="[#if item.slug == currentStage]currentSection[/#if] ${(item.active)?string('enabled','disabled')}">
            <a href="[@s.url action="${centerSession}/${item.action}"][@s.param name="programID" value=programID /][@s.param name="edit" value="true"/][/@s.url]" onclick="return ${item.active?string}">
              [@s.text name=item.name/]
            </a>
          </li>
        [/#list] 
      </ul>
    </li>
  </ul> 
</nav>

<div class="clearfix"></div>