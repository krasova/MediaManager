import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Picture from './picture';
import Movie from './movie';

const Routes = ({ match }) => (
  <div>
    <Switch>
      <ErrorBoundaryRoute path={`${match.url}/picture`} component={Picture} />
      <ErrorBoundaryRoute path={`${match.url}/movie`} component={Movie} />
    </Switch>
  </div>
);

export default Routes;
