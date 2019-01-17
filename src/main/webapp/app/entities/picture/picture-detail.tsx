import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './picture.reducer';

export interface IPictureDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PictureDetail extends React.Component<IPictureDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { pictureEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Picture [<b>{pictureEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{pictureEntity.name}</dd>
            <dt>
              <span id="path">Path</span>
            </dt>
            <dd>{pictureEntity.path}</dd>
            <img src={'file:///' + pictureEntity.path} height="420" width="420"/>
          </dl>
          <Button tag={Link} to="/entity/picture" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/picture/${pictureEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ picture }: IRootState) => ({
  pictureEntity: picture.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PictureDetail);
