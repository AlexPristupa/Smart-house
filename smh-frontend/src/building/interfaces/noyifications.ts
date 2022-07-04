import { IHardware, IService } from '.';

export interface INotification {
  type: string | 'HARDWARE_EXPIRED' | 'SERVICE_WORK_BEFORE_START' | 'SERVICE_WORK_FINISHED';
  read: Boolean;
  facilityId: number;
  id: number;
  payload: IHardware | IService;
}
export interface INotificationsList {
  content: Array<INotification>;
}
