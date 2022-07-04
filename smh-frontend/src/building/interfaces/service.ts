export interface IServiceType {
  id: number;
  name: string;
  description: string | null;
  icon?: string | null;
  pinned?: boolean;
  linked?: boolean;
}

export enum SERVICE_RESOLUTION {
  RESOLVED = 'RESOLVED',
  UNRESOLVED = 'UNRESOLVED',
}

export enum SERVICE_STATUS {
  FINISHED = 'FINISHED',
  IN_PROGRESS = 'IN_PROGRESS',
  PENDING = 'PENDING',
}

export interface IService {
  id: number;
  name: string;
  description: string;
  finishTime: string;
  resolution: SERVICE_RESOLUTION;
  resolutionTime: string | null;
  startTime: string;
  status: SERVICE_STATUS;
  type: IServiceType;
}

export interface IServiceForm extends Omit<IService, 'type'> {
  serviceWorkType: number;
  timeStart: string;
  timeFinish: string;
}
