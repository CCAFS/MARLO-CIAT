[#ftl]
[#assign items= [
  { 'slug': 'Capacity Development',           'name': 'capdev.menu.hrefCapacityDevelopment',  'action': '','active': true  },
  { 'slug': 'statistics',  'name': 'capdev.menu.hrefStatistics',       'action': '',  'active': true }
]/]




<nav id="secondaryMenu" class="">
  <p>[@s.text name="capdev.menu.title"/]</p>
  <ul>
    <li>
      <ul>
        [#list items as item]
          
          <li>
            <a>
              [@s.text name=item.name /] 
            </a>
          </li>
          
        [/#list] 
      </ul>
    </li>
  </ul> 
</nav>