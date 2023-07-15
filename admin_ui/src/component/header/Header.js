import {Nav, Navbar, NavbarBrand, NavItem, NavLink} from "reactstrap";

const Header = props => {
  return(
      <header>
          <Navbar className="header" expand="md" container="md" fixed="top">
              <NavbarBrand className="navbar-brand" href="/">Signals</NavbarBrand>
              <Nav>
                  <NavItem>
                      <NavLink className="nav-link" href="/config/">Config</NavLink>
                  </NavItem>
              </Nav>
          </Navbar>
      </header>

  )
}

export default Header;