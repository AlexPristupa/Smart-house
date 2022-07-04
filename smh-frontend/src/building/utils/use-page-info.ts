import { BUILDNG_CONTENTS, PAGE_TYPE } from 'building/constants';
import { IBuildingUrlParams, PAGE_ACTIONS } from 'building/interfaces';
import { useParams } from 'react-router-dom';

export const usePageInfo = (): keyof typeof PAGE_TYPE => {
  const urlParams = useParams<IBuildingUrlParams>();

  const { buildingId, contents, contentId, action } = urlParams;

  if (buildingId === 'new') {
    return 'BUILDING_ADD';
  } else if (buildingId && contents === 'edit') {
    return 'BUILDING_EDIT';
  }

  if (buildingId && contentId && action === 'edit') {
    switch (contents) {
      case BUILDNG_CONTENTS.HARDWARE:
        return 'HARDWARE_EDIT';
      case BUILDNG_CONTENTS.SERVICE:
        return 'SERVICE_EDIT';
    }
  }

  if (buildingId && contentId) {
    if (contentId === PAGE_ACTIONS.NEW) {
      switch (contents) {
        case BUILDNG_CONTENTS.HARDWARE:
          return 'HARDWARE_ADD';
        case BUILDNG_CONTENTS.SERVICE:
          return 'SERVICE_ADD';
      }
    }

    switch (contents) {
      case BUILDNG_CONTENTS.HARDWARE: {
        if (contentId && action === 'edit') {
          return 'HARDWARE_EDIT';
        } else if (contentId === 'new') {
          return 'HARDWARE_ADD';
        }
        return 'HARDWARE';
      }
      case BUILDNG_CONTENTS.SERVICE:
        return 'SERVICE';
    }
  }

  if (buildingId) {
    return 'BUILDING';
  }

  return 'BUILDINGS_LIST';
};
