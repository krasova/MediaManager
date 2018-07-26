import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Folder from './folder';
import FolderDetail from './folder-detail';
import FolderDeleteDialog from './folder-delete-dialog';
import FolderUpload from './folder-upload';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/upload`} component={FolderUpload} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FolderDetail} />
      <ErrorBoundaryRoute path={match.url} component={Folder} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FolderDeleteDialog} />
  </>
);

export default Routes;
