export enum PAGE_ACTIONS {
  NEW = 'new',
  EDIT = 'edit',
}

export interface IBuildingUrlParams {
  buildingId?: string | PAGE_ACTIONS.NEW;
  contents?: string | PAGE_ACTIONS.EDIT;
  contentId?: string | PAGE_ACTIONS.NEW;
  action?: PAGE_ACTIONS.EDIT;
}
