import { PAGE_TYPE } from 'building/constants';
import { Building } from './building';
import { BuildingsList } from './buildings-list';
import { AddBuilding } from './buildings-list/add-building';
import { EditBuilding } from './buildings-list/edit-building';
import { CardHardware } from './hardware';
import { CardService } from './services';
import { AddService } from './services/add-service';
import { EditService } from './services/edit-service';
import { AddHardware } from './hardware/add-hardware';
import { EditHardware } from './hardware/edit-hardware';

export const PAGE_CONTENT: Record<keyof typeof PAGE_TYPE, React.FC> = {
  BUILDINGS_LIST: BuildingsList,
  BUILDING: Building,
  BUILDING_ADD: AddBuilding,
  BUILDING_EDIT: EditBuilding,
  HARDWARE: CardHardware,
  HARDWARE_ADD: AddHardware,
  HARDWARE_EDIT: EditHardware,
  SERVICE: CardService,
  SERVICE_ADD: AddService,
  SERVICE_EDIT: EditService,
};
