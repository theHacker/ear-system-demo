<template>
    <div>
        <UContainer class="my-4">
            <h1 class="text-4xl font-bold py-2">Quantity Reports</h1>

            <UTable
                :loading="loading"
                :columns="tableColumns"
                :data="quantityReports"
            />
        </UContainer>

        <UContainer class="my-8">
            <h2 class="text-xl font-bold py-2">Add demo quantity report</h2>

            <UButton @click="addQuantityReport">
                Send demo request to add a quantity report
            </UButton>
        </UContainer>
    </div>
</template>

<script setup lang="ts">
import type { TableColumn } from '@nuxt/ui';

type QuantityReport = {
    amount: number;
    unit: 'KILOGRAM' | 'PIECE' | 'TON';
    yearMonth: string;
    deviceTypeNumber: number;
    registrationNumber: number;
    declarationOfTruth: boolean;
};

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

function formatAmount(amount: integer, unit: 'KILOGRAM' | 'PIECE' | 'TON'): string {
    let suffix = '';
    switch (unit) {
        case 'KILOGRAM': suffix = 'kg'; break;
        case 'PIECE': suffix = ''; break;
        case 'TON': suffix = 't'; break;
    }

    return new Intl.NumberFormat('en', { useGrouping: true }).format(amount) + suffix;
}

function formatYearMonth(yearMonth: string): string {
    const year = parseInt(yearMonth.substring(0, 4));
    const month = parseInt(yearMonth.substring(5));

    return new Intl.DateTimeFormat('en', {
        month: 'long',
        year: 'numeric',
        timeZone: 'UTC'
    }).format(new Date(Date.UTC(year, month - 1, 1)));
}

const loading = ref<boolean>(false);
const quantityReports = ref<QuantityReport[]>([]);

onMounted(() => {
    loadQuantityReports();
});

async function loadQuantityReports() {
    loading.value = true;

    try {
        quantityReports.value = await $fetch('/api/quantityReports');
    } catch (error) {
        console.error('Request failed:', error);
        // TODO error management
    } finally {
        loading.value = false;
    }
}

async function addQuantityReport() {
    try {
        await $fetch('/api/quantityReports', {
            method: 'POST',
            body: {
                'amount': 99.4,
                'unit': 'TON',
                'yearMonth': "2012-04",
                'deviceTypeNumber': 123456,
                'registrationNumber': 987654,
                'declarationOfTruth': true
            } satisfies QuantityReport,
        });
    } catch (error) {
        console.error('Request failed:', error);
        // TODO error management
    }
}
</script>
