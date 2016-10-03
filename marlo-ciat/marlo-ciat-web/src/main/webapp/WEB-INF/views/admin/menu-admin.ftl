[#ftl]
[#assign items= [
  { 'slug': 'management',       'name': 'CRPAdmin.menu.hrefProgramManagement', 'action': 'management',       'active': true },
  { 'slug': 'siteIntegration',  'name': 'CRPAdmin.menu.hrefResearchManagement',   'action': 'siteIntegration',  'active': true },
  { 'slug': 'ppaPartners',      'name': 'CRPAdmin.menu.hrefStrategicObjectives',       'action': 'ppaPartners',      'active': true }
]/]

<nav id="secondaryMenu">
  <p>[@s.text name="CRPAdmin.menu.title"/]</p>
  <ul>
    <li>
      <ul>
        [#list items as item]
          <li id="${item.slug}" class="[#if item.slug == currentStage]currentSection[/#if] ${(item.active)?string('enabled','disabled')}">
            <a href="[@s.url action="${crpSession}/${item.action}"][@s.param name="edit" value="true"/][/@s.url]" onclick="return ${item.active?string}">
              [@s.text name=item.name/]
            </a>
          </li>
        [/#list] 
      </ul>
    </li>
  </ul> 
</nav>

<div class="clearfix"></div>