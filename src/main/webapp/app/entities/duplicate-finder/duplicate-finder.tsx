import React from 'react';
import { Row } from 'reactstrap';
// tslint:disable-next-line:no-submodule-imports
import { TreeTable } from 'primereact/treetable';
// tslint:disable-next-line:no-submodule-imports
import { Column } from 'primereact/column';
// tslint:disable-next-line:no-submodule-imports
import { Button } from 'primereact/button';
// tslint:disable-next-line:no-submodule-imports
import TreeNode from 'primereact/components/treenode/TreeNode';

export default interface IDuplicateFinderState {
  nodes: TreeNode[];
  expandedKeys: string[];
}

export class DuplicateFinder extends React.Component<IDuplicateFinderState> {
  state: IDuplicateFinderState = {
    nodes: [] as any,
    expandedKeys: [] as any
  };

toggleApplications() {
    const expandedKeys = { ...this.state.expandedKeys };
    if (expandedKeys['0']) {
        delete expandedKeys['0'];
    } else {
        expandedKeys['0'] = true;
    }

    this.setState({ expandedKeys });
}

componentDidMount() {
    this.setState({ nodes: [
      {
          'key': '0',
          'data': {
              'name': 'Applications',
              'size': '100kb',
              'type': 'Folder'
          },
          'children': [
              {
                  'key': '0-0',
                  'data': {
                      'name': 'React',
                      'size': '25kb',
                      'type': 'Folder'
                  },
                  'children': [
                      {
                          'key': '0-0-0',
                          'data': {
                              'name': 'react.app',
                              'size': '10kb',
                              'type': 'Application'
                          }
                      },
                      {
                          'key': '0-0-1',
                          'data': {
                              'name': 'native.app',
                              'size': '10kb',
                              'type': 'Application'
                          }
                      },
                      {
                          'key': '0-0-2',
                          'data': {
                              'name': 'mobile.app',
                              'size': '5kb',
                              'type': 'Application'
                          }
                      }
                  ]
              },
              {
                  'key': '0-1',
                  'data': {
                      'name': 'editor.app',
                      'size': '25kb',
                      'type': 'Application'
                  }
              },
              {
                  'key': '0-2',
                  'data': {
                      'name': 'settings.app',
                      'size': '50kb',
                      'type': 'Application'
                  }
              }
          ]
      },
      {
          'key': '1',
          'data': {
              'name': 'Cloud',
              'size': '20kb',
              'type': 'Folder'
          },
          'children': [
              {
                  'key': '1-0',
                  'data': {
                      'name': 'backup-1.zip',
                      'size': '10kb',
                      'type': 'Zip'
                  }
              },
              {
                  'key': '1-1',
                  'data': {
                      'name': 'backup-2.zip',
                      'size': '10kb',
                      'type': 'Zip'
                  }
              }
          ]
      },
      {
          'key': '2',
          'data': {
              'name': 'Desktop',
              'size': '150kb',
              'type': 'Folder'
          },
          'children': [
              {
                  'key': '2-0',
                  'data': {
                      'name': 'note-meeting.txt',
                      'size': '50kb',
                      'type': 'Text'
                  }
              },
              {
                  'key': '2-1',
                  'data': {
                      'name': 'note-todo.txt',
                      'size': '100kb',
                      'type': 'Text'
                  }
              }
          ]
      },
      {
          'key': '3',
          'data': {
              'name': 'Documents',
              'size': '75kb',
              'type': 'Folder'
          },
          'children': [
              {
                  'key': '3-0',
                  'data': {
                      'name': 'Work',
                      'size': '55kb',
                      'type': 'Folder'
                  },
                  'children': [
                      {
                          'key': '3-0-0',
                          'data': {
                              'name': 'Expenses.doc',
                              'size': '30kb',
                              'type': 'Document'
                          }
                      },
                      {
                          'key': '3-0-1',
                          'data': {
                              'name': 'Resume.doc',
                              'size': '25kb',
                              'type': 'Resume'
                          }
                      }
                  ]
              },
              {
                  'key': '3-1',
                  'data': {
                      'name': 'Home',
                      'size': '20kb',
                      'type': 'Folder'
                  },
                  'children': [
                      {
                          'key': '3-1-0',
                          'data': {
                              'name': 'Invoices',
                              'size': '20kb',
                              'type': 'Text'
                          }
                      }
                  ]
              }
          ]
      },
      {
          'key': '4',
          'data': {
              'name': 'Downloads',
              'size': '25kb',
              'type': 'Folder'
          },
          'children': [
              {
                  'key': '4-0',
                  'data': {
                      'name': 'Spanish',
                      'size': '10kb',
                      'type': 'Folder'
                  },
                  'children': [
                      {
                          'key': '4-0-0',
                          'data': {
                              'name': 'tutorial-a1.txt',
                              'size': '5kb',
                              'type': 'Text'
                          }
                      },
                      {
                          'key': '4-0-1',
                          'data': {
                              'name': 'tutorial-a2.txt',
                              'size': '5kb',
                              'type': 'Text'
                          }
                      }
                  ]
              },
              {
                  'key': '4-1',
                  'data': {
                      'name': 'Travel',
                      'size': '15kb',
                      'type': 'Text'
                  },
                  'children': [
                      {
                          'key': '4-1-0',
                          'data': {
                              'name': 'Hotel.pdf',
                              'size': '10kb',
                              'type': 'PDF'
                          }
                      },
                      {
                          'key': '4-1-1',
                          'data': {
                              'name': 'Flight.pdf',
                              'size': '5kb',
                              'type': 'PDF'
                          }
                      }
                  ]
              }
          ]
      },
      {
          'key': '5',
          'data': {
              'name': 'Main',
              'size': '50kb',
              'type': 'Folder'
          },
          'children': [
              {
                  'key': '5-0',
                  'data': {
                      'name': 'bin',
                      'size': '50kb',
                      'type': 'Link'
                  }
              },
              {
                  'key': '5-1',
                  'data': {
                      'name': 'etc',
                      'size': '100kb',
                      'type': 'Link'
                  }
              },
              {
                  'key': '5-2',
                  'data': {
                      'name': 'var',
                      'size': '100kb',
                      'type': 'Link'
                  }
              }
          ]
      },
      {
          'key': '6',
          'data': {
              'name': 'Other',
              'size': '5kb',
              'type': 'Folder'
          },
          'children': [
              {
                  'key': '6-0',
                  'data': {
                      'name': 'todo.txt',
                      'size': '3kb',
                      'type': 'Text'
                  }
              },
              {
                  'key': '6-1',
                  'data': {
                      'name': 'logo.png',
                      'size': '2kb',
                      'type': 'Picture'
                  }
              }
          ]
      },
      {
          'key': '7',
          'data': {
              'name': 'Pictures',
              'size': '150kb',
              'type': 'Folder'
          },
          'children': [
              {
                  'key': '7-0',
                  'data': {
                      'name': 'barcelona.jpg',
                      'size': '90kb',
                      'type': 'Picture'
                  }
              },
              {
                  'key': '7-1',
                  'data': {
                      'name': 'primeng.png',
                      'size': '30kb',
                      'type': 'Picture'
                  }
              },
              {
                  'key': '7-2',
                  'data': {
                      'name': 'prime.jpg',
                      'size': '30kb',
                      'type': 'Picture'
                  }
              }
          ]
      },
      {
          'key': '8',
          'data': {
              'name': 'Videos',
              'size': '1500kb',
              'type': 'Folder'
          },
          'children': [
              {
                  'key': '8-0',
                  'data': {
                      'name': 'primefaces.mkv',
                      'size': '1000kb',
                      'type': 'Video'
                  }
              },
              {
                  'key': '8-1',
                  'data': {
                      'name': 'intro.avi',
                      'size': '500kb',
                      'type': 'Video'
                  }
              }
          ]
      }
  ] });
}

  render() {
    return (
      <div>
        <Row>
                <div className="content-section introduction">
                    <div className="feature-intro">
                        <h1>TreeTable</h1>
                        <p>TreeTable is used to display hierarchical data in tabular format.</p>
                    </div>
                </div>

                <div className="content-section implementation">
                    <h3>Uncontrolled</h3>
                    <TreeTable value={this.state.nodes}>
                        <Column field="name" header="Name" expander/>
                        <Column field="size" header="Size"/>
                        <Column field="type" header="Type"/>
                    </TreeTable>

                    <h3>Controlled</h3>
                    <Button onClick={this.toggleApplications} label="Toggle Applications" />
                    <TreeTable value={this.state.nodes} expandedKeys={this.state.expandedKeys}
                        // tslint:disable-next-line:jsx-no-lambda
                        onToggle={e => this.setState({ expandedKeys: e.value })} style={{ marginTop: '.5em' }}>
                        <Column field="name" header="Name" expander/>
                        <Column field="size" header="Size"/>
                        <Column field="type" header="Type"/>
                    </TreeTable>
                </div>
        </Row>
        <Row>2</Row>
      </div>
    );
  }
}
