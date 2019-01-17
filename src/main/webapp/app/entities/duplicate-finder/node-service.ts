import axios from 'axios';

export function getTreeTableNodes() {
        // tslint:disable-next-line:max-line-length
        return axios.get('sdfs')
                .then(res => res.data.root);
    }
