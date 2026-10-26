import { expect, it, describe } from 'vitest';
import { formatYearMonth, buildYearMonth } from './quantity-reports';

describe('formatYearMonth()', () => {
    it('formats correctly in "monthName yyyy" format', () => {
        expect(formatYearMonth('2009-11')).toBe('November 2009');
    });

    it('will not check for invalid input', () => {
        expect(formatYearMonth('2000-15')).toBe('March 2001'); // produces overflow
        expect(() => formatYearMonth('shittyInput🤣')).toThrow();
    });
});

describe('buildYearMonth()', () => {
    it('formats correctly in yyyy-mm format', () => {
        expect(buildYearMonth(2009, 11)).toBe('2009-11');
    });

    it('will pad months a leading zero if needed', () => {
        expect(buildYearMonth(2000, 4)).toBe('2000-04');
    });

    it('will not check for invalid input', () => {
        expect(buildYearMonth(2000, 99)).toBe('2000-99');
        expect(buildYearMonth(6666666, 777)).toBe('6666666-777');
        expect(buildYearMonth(-1, -5)).toBe('-1--5');
    });
});
