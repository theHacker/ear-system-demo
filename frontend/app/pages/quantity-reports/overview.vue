<template>
    <div>
        <UContainer class="my-4">
            <h1 class="text-4xl font-bold py-2">
                Quantity Reports
            </h1>

            <UAlert
                v-if="errorMessage !== null"
                color="error"
                variant="solid"
                title="Error"
                :description="errorMessage"
                icon="lucide:triangle-alert"
            />

            <UTable
                :loading="loading"
                :columns="tableColumns"
                :data="quantityReports"
            />
        </UContainer>
    </div>
</template>

<script setup lang="ts">
import type { TableColumn } from '@nuxt/ui';
import type { QuantityReport } from '~/utils/quantity-reports.ts';
import { formatAmount, formatYearMonth } from '~/utils/quantity-reports.ts';

const tableColumns: TableColumn<QuantityReport>[] = [
    {
        header: 'Month',
        accessorFn: row => formatYearMonth(row.yearMonth)
    },
    {
        header: 'Amount',
        accessorFn: row => formatAmount(row.amount, row.unit)
    },
    {
        accessorKey: 'deviceTypeNumber',
        header: 'Device type number'
    },
    {
        accessorKey: 'registrationNumber',
        header: 'Registration number'
    },
    {
        accessorKey: 'declarationOfTruth',
        header: 'Declaration of truth',
        meta: {
            class: {
                th: 'text-center',
                td: 'text-center',
            }
        },
        cell: ({ getValue }) => getValue() ? '✅️' : '❌️'
    },
];

const loading = ref<boolean>(false);
const errorMessage = ref<string | null>(null);
const quantityReports = ref<QuantityReport[]>([]);

onMounted(() => {
    loadQuantityReports();
});

async function loadQuantityReports() {
    loading.value = true;

    try {
        quantityReports.value = await $fetch('/api/quantityReports');
    } catch (error) {
        errorMessage.value = error instanceof Error ? error.message : String(error);
    } finally {
        loading.value = false;
    }
}
</script>
