import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { SERVER_API_URL } from 'app/config/constants';

import { IFolder, defaultValue } from 'app/shared/model/folder.model';

export const ACTION_TYPES = {
  SEARCH_FOLDERS: 'folder/SEARCH_FOLDERS',
  FETCH_FOLDER_LIST: 'folder/FETCH_FOLDER_LIST',
  FETCH_FOLDER: 'folder/FETCH_FOLDER',
  UPLOAD_FOLDER: 'folder/UPLOAD_FOLDER',
  DELETE_FOLDER: 'folder/DELETE_FOLDER',
  RESET: 'folder/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFolder>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type FolderState = Readonly<typeof initialState>;

// Reducer

export default (state: FolderState = initialState, action): FolderState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_FOLDERS):
    case REQUEST(ACTION_TYPES.FETCH_FOLDER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FOLDER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.UPLOAD_FOLDER):
    case REQUEST(ACTION_TYPES.DELETE_FOLDER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_FOLDERS):
    case FAILURE(ACTION_TYPES.FETCH_FOLDER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FOLDER):
    case FAILURE(ACTION_TYPES.UPLOAD_FOLDER):
    case FAILURE(ACTION_TYPES.DELETE_FOLDER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_FOLDERS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FOLDER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FOLDER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.UPLOAD_FOLDER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FOLDER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = SERVER_API_URL + '/api/folders';
const apiSearchUrl = SERVER_API_URL + '/api/_search/folders';

// Actions

export const getSearchEntities: ICrudSearchAction<IFolder> = query => ({
  type: ACTION_TYPES.SEARCH_FOLDERS,
  payload: axios.get<IFolder>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IFolder> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FOLDER_LIST,
  payload: axios.get<IFolder>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IFolder> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FOLDER,
    payload: axios.get<IFolder>(requestUrl)
  };
};

export const uploadEntity: ICrudPutAction<IFolder> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPLOAD_FOLDER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFolder> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FOLDER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
