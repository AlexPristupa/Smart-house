import { createBrowserHistory } from 'history';

export const history = createBrowserHistory();

export const historyWithRefresh = createBrowserHistory({ forceRefresh: true });

export const goToLink = (link: string) => () => {
  history.push(link);
};
