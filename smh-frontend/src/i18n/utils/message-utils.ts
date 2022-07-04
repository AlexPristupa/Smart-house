export type PluralFormatCallback<R> = (quantity: number) => R;

export const selectPluralMessage = <T>(
  quantity: number,
  one: PluralFormatCallback<T>,
  two: PluralFormatCallback<T>,
  five: PluralFormatCallback<T>,
): T => {
  const mod100 = Math.abs(quantity) % 100;
  const mod10 = Math.abs(quantity) % 10;

  // - 5,6,7,8,9 обращений
  // - 10-20 обращений
  if (mod100 >= 5 && mod100 <= 20) {
    return five(quantity);
  }

  // - 1 обращение
  // - 21 обращение
  // - ...
  // - 91 обращение
  if (mod10 === 1) {
    return one(quantity);
  }

  // - 2,3,4 обращения
  // - 22,23,24 обращения
  // - ...
  // - 92,93,94 обращения
  if (mod10 >= 2 && mod10 <= 4) {
    return two(quantity);
  }

  // - 0 обращений
  // - (20),25,26,27,28,29 обращений
  // - ...
  // - 90,95,96,97,98,99 обращений
  return five(quantity);
};

export class FormattedPluralMessageSelector<T> {
  constructor(private one: PluralFormatCallback<T>, private two: PluralFormatCallback<T>, private five: PluralFormatCallback<T>) {}

  public getMessage(quantity: number): T {
    return selectPluralMessage(quantity, this.one, this.two, this.five);
  }
}

export class PluralMessageSelector<T> extends FormattedPluralMessageSelector<T> {
  constructor(one: T, two: T, five: T) {
    super(
      quantity => one,
      quantity => two,
      quantity => five,
    );
  }
}
