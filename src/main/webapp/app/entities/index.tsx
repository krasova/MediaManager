import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Picture from './picture';
import Movie from './movie';
import Folder from './folder';

const Routes = ({ match }) => (
  <div>
    <Switch>
      <ErrorBoundaryRoute path={`${match.url}/picture`} component={Picture} />
      <ErrorBoundaryRoute path={`${match.url}/movie`} component={Movie} />
      <ErrorBoundaryRoute path={`${match.url}/folder`} component={Folder} />
    </Switch>
  </div>
);

export default Routes;
