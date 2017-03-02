[#ftl]

  [#assign menus= [
    { 'title': 'General Information', 'show': true,
      'items': [
      { 'slug': 'description',  'name': 'projects.menu.description',  'action': 'description',  'active': true  },
      { 'slug': 'projectPartners',  'name': 'projects.menu.partners',  'action': 'projectPartners',  'active': false  },
      { 'slug': 'deliverables',  'name': 'projects.menu.deliverables',  'action': 'deliverable',  'active': false  }
      ]
    }
    
  ]/]

[#-- Menu--]
<nav id="secondaryMenu" class="">
  <p>Project Menu </p> 
  <ul>
    [#list menus as menu]
      [#if menu.show]
      <li>
        <ul><p class="menuTitle">${menu.title}</p>
          [#list menu.items as item]
            [#if (item.show)!true ]
              <li id="menu-${item.action}" class="[#if item.slug == currentStage]currentSection[/#if] ${(item.active)?string('enabled','disabled')}">
                <a href="[@s.url action="${centerSession}/${item.action}"][@s.param name="projectID" value=projectID /][@s.param name="edit" value="true"/][/@s.url]" onclick="return ${item.active?string}" class="action-${centerSession}/${item.action}">
                  [@s.text name=item.name/]
                </a>
              </li>
            [/#if]
          [/#list] 
        </ul>
      </li>
      [/#if]
    [/#list]
  </ul> 
</nav>

<div class="clearfix"></div>






[#-- Project Submit JS 
[#assign customJS = [ "${baseUrl}/js/projects/projectSubmit.js" ] + customJS  / --]
