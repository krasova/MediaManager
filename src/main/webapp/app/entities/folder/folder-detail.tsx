import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './folder.reducer';

export interface IFolderDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FolderDetail extends React.Component<IFolderDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { folderEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Folder [<b>{folderEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="path">Path</span>
            </dt>
            <dd>{folderEntity.path}</dd>
          </dl>
          <Button tag={Link} to="/entity/folder" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/folder/${folderEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ folder }: IRootState) => ({
  folderEntity: folder.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FolderDetail);
