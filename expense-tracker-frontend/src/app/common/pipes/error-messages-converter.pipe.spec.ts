import { ErrorMessagesConverterPipe } from './error-messages-converter.pipe';

describe('ErrorMessagesConverterPipe', () => {
  it('create an instance', () => {
    const pipe = new ErrorMessagesConverterPipe();
    expect(pipe).toBeTruthy();
  });
});
