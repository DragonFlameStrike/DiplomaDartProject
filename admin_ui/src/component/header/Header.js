import {Nav, Navbar, NavbarBrand, NavItem, NavLink} from "reactstrap";

const Header = props => {
  return(
      <header className="header">
          <Navbar color="dark" dark full="false" expand="md" container="md" fixed="top">
              <NavbarBrand href="/">Signals</NavbarBrand>
              <Nav >
                  <NavItem>
                      <NavLink href="/config/">Config</NavLink>
                  </NavItem>
              </Nav>
          </Navbar>
      </header>
  )
}

export default Header;