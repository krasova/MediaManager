import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import { DuplicateFinder } from './duplicate-finder';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute path={match.url} component={DuplicateFinder} />
    </Switch>
  </>
);

export default Routes;
