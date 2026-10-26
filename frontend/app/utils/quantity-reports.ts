export type QuantityReport = {
    amount: number;
    unit: 'KILOGRAM' | 'PIECE' | 'TON';
    yearMonth: string;
    deviceTypeNumber: number;
    registrationNumber: number;
    declarationOfTruth: boolean;
};

/**
 * Allowed unit suffixes for amounts.
 * Key is the technical enum value, value is the human-readable suffix.
 */
export const unitSuffixes = {
    KILOGRAM: 'kg',
    PIECE: '',
    TON: 't'
};

/**
 * Formats a given `amount` and `unit` into a human-readable representation like `1,234kg`.
 */
export function formatAmount(amount: number, unit: 'KILOGRAM' | 'PIECE' | 'TON'): string {
    const suffix = unitSuffixes[unit];

    return new Intl.NumberFormat('en', { useGrouping: true }).format(amount) + suffix;
}

/**
 * Formats a month number (1-12) into a human-readable representation like `April`.
 */
export function formatMonth(month: number): string {
    return new Intl.DateTimeFormat('en', {
        month: 'long'
    }).format(new Date(Date.UTC(2000, month - 1, 1)));
}

/**
 * Formats a `yyyy-mm` string into a human-readable representation like `April 2012`.
 */
export function formatYearMonth(yearMonth: string): string {
    const year = parseInt(yearMonth.substring(0, 4));
    const month = parseInt(yearMonth.substring(5));

    return new Intl.DateTimeFormat('en', {
        month: 'long',
        year: 'numeric',
        timeZone: 'UTC'
    }).format(new Date(Date.UTC(year, month - 1, 1)));
}

/**
 * Constructs a `yyyy-mm` string from year and month as integers.
 */
export function buildYearMonth(year: number, month: number): string {
    return String(year) + '-' + String(month).padStart(2, '0');
}
