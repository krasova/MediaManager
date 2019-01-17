import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Picture from './picture';
import Movie from './movie';
import Folder from './folder';
import { DuplicateFinder } from './duplicate-finder/duplicate-finder';

const Routes = ({ match }) => (
  <div>
    <Switch>
      <ErrorBoundaryRoute path={`${match.url}/picture`} component={Picture} />
      <ErrorBoundaryRoute path={`${match.url}/movie`} component={Movie} />
      <ErrorBoundaryRoute path={`${match.url}/folder`} component={Folder} />
      <ErrorBoundaryRoute path={`${match.url}/duplicateFinder`} component={DuplicateFinder} />
    </Switch>
  </div>
);

export default Routes;
