import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = () => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/picture">
      <FontAwesomeIcon icon="asterisk" />&nbsp; Picture
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/movie">
      <FontAwesomeIcon icon="asterisk" />&nbsp; Movie
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/folder">
      <FontAwesomeIcon icon="asterisk" />&nbsp; Folder
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/duplicateFinder">
      <FontAwesomeIcon icon="asterisk" />&nbsp; Duplicate Finder
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
