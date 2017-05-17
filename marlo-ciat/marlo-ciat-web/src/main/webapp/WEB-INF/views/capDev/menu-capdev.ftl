[#ftl]
[#assign objs= [
  { 'slug': 'Capacity Development',           'name': 'capdev.menu.hrefCapdev',  'action': '',           'active': true  },
  { 'slug': 'Statistics',  'name': 'capdev.menu.hrefStatistics',       'action': '',  'active': true }
  
]/]





<nav id="secondaryMenu" class="">
  <p>[@s.text name="capdev.menu.title"/]</p>
  <ul>
    <li>
      <ul>
          
          <li>
            <a href="#">
              [@s.text name="capdev.menu.hrefCapdev" /]
            </a>
            
          </li>
          <li>
            <a href="#">
              [@s.text name="capdev.menu.hrefStatistics" /]
            </a>
            
          </li>
          
      </ul>
    </li>
  </ul> 
</nav>