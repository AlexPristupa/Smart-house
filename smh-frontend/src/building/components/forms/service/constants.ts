import { IServiceForm } from 'building/interfaces';

enum SERVICE_KEYS {
  NAME,
  DESCRIPTION,
  RESOLUTION,
  START_TIME,
  RESOLUTION_TIME,
  FINISH_TIME,
  TYPE,
  TIME_START,
  TIME_FINISH,
}

export const SERVICE_FORM_FIELDS: Record<keyof typeof SERVICE_KEYS, keyof Partial<IServiceForm>> = {
  NAME: 'name',
  DESCRIPTION: 'description',
  RESOLUTION: 'resolution',
  START_TIME: 'startTime',
  RESOLUTION_TIME: 'resolutionTime',
  FINISH_TIME: 'finishTime',
  TYPE: 'serviceWorkType',
  TIME_START: 'timeStart',
  TIME_FINISH: 'timeFinish',
};
