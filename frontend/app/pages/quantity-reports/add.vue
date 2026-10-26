<template>
    <div>
        <UContainer class="my-4">
            <h1 class="text-4xl font-bold py-2">
                Add new Quantity Report
            </h1>

            <UAlert
                v-if="errorMessage !== null"
                color="error"
                variant="solid"
                title="Error"
                :description="errorMessage"
                icon="lucide:triangle-alert"
            />

            <UForm
                :schema="FormStateSchema"
                :state="formState"
                :disabled="loading"
                class="my-6"
                @submit="onSubmit"
            >
                <div class="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
                    <UFormField label="Amount" name="amount">
                        <UInput v-model.number="formState.amount" />
                    </UFormField>

                    <UFormField label="Unit" name="unit">
                        <USelect
                            v-model="formState.unit"
                            :items="units"
                            class="w-40"
                        />
                    </UFormField>

                    <UFormField label="Year" name="year">
                        <UInput v-model.number="formState.year" />
                    </UFormField>

                    <UFormField label="Year" name="month">
                        <USelectMenu
                            v-model="formState.month"
                            :items="months"
                            class="w-32"
                            value-key="value"
                        />
                    </UFormField>

                    <UFormField label="Device type number" name="deviceTypeNumber">
                        <UInput v-model.number="formState.deviceTypeNumber" />
                    </UFormField>

                    <UFormField label="Registration number" name="registrationNumber">
                        <UInput v-model.number="formState.registrationNumber" />
                    </UFormField>

                    <UFormField label="Declaration of Truth" name="declarationOfTruth">
                        <UCheckbox v-model="formState.declarationOfTruth" />
                    </UFormField>

                    <div class="col-span-full" />

                    <UButton type="submit">
                        Submit
                    </UButton>
                </div>
            </UForm>
        </UContainer>
    </div>
</template>

<script setup lang="ts">
import * as v from 'valibot';
import { unitSuffixes, formatMonth, buildYearMonth } from '~/utils/quantity-reports.ts';
import type { QuantityReport } from '~/utils/quantity-reports.ts';

const FormStateSchema = v.pipe(
    v.object({
        amount: v.number(),
        unit: v.picklist(Object.keys(unitSuffixes)),
        year: v.pipe(
            v.number(),
            v.integer(),
            v.minValue(2000, 'Years before 2000 are not allowed.'),
            v.maxValue(2100, 'Invalid year.')
        ),
        month: v.pipe(
            v.number(),
            v.integer(),
            v.minValue(1, 'Invalid month.'),
            v.maxValue(12, 'Invalid month.')
        ),
        deviceTypeNumber: v.number(),
        registrationNumber: v.number(),
        declarationOfTruth: v.boolean()
    }),
    v.forward( // TODO Note: letting Nuxt UI call the validation on focus leave, there is the issue "changing the type will not mark amount as error"
        v.partialCheck(
            [['amount'], ['unit']],
            (input) => {
                if (input.amount === undefined) {
                    return false;
                }

                const amountString = input.amount.toString();
                const decimalPlaces = amountString.includes('.') ? input.amount.toString().replace(/\d+\./, '').length : 0; // TODO can be more robust ;-)

                let maxDecimalPlaces;
                switch (input.unit) {
                    case 'KILOGRAM':
                        maxDecimalPlaces = 3;
                        break;
                    case 'PIECE':
                        maxDecimalPlaces = 0;
                        break;
                    case 'TON':
                        maxDecimalPlaces = 6;
                        break;
                    default:
                        return false; // should not be reached, because previous validation should have complained
                }

                return (decimalPlaces <= maxDecimalPlaces);
            },
            'Amount has invalid number of decimal places.' // TODO not nice to have a common message, rather than "for kilograms at most 3 decimal places"
        ),
        ['amount']
    )
);

const loading = ref<boolean>(false);
const errorMessage = ref<string | null>(null);

const formState = reactive({
    amount: undefined,
    unit: undefined,
    year: new Date().getFullYear(),
    month: new Date().getMonth() + 1,
    deviceTypeNumber: undefined,
    registrationNumber: undefined,
    declarationOfTruth: undefined
});

const router = useRouter();

const units = [
    { label: 'kg', value: 'KILOGRAM' },
    { label: 'pieces', value: 'PIECE' },
    { label: 't', value: 'TON' },
];

const months = Array.from({ length: 12 }, (_, i) => ({ label: formatMonth(i + 1), value: i + 1 }));

async function onSubmit() {
    const validatedData = v.parse(FormStateSchema, formState); // Note: Should be no error, as Nuxt UI already validated

    loading.value = true;
    try {
        await $fetch('/api/quantityReports', {
            method: 'POST',
            body: {
                amount: validatedData.amount,
                unit: validatedData.unit as 'KILOGRAM' | 'PIECE' | 'TON', // TODO nasty cast, likely there is a better way
                yearMonth: buildYearMonth(validatedData.year, validatedData.month),
                deviceTypeNumber: validatedData.deviceTypeNumber,
                registrationNumber: validatedData.registrationNumber,
                declarationOfTruth: validatedData.declarationOfTruth
            } satisfies QuantityReport
        });
        router.push('/quantity-reports/overview');
    } catch (error) {
        errorMessage.value = error instanceof Error ? error.message : String(error);
    } finally {
        loading.value = false;
    }
}
</script>
