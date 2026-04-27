import React, { useCallback, useEffect, useState } from 'react';
import {
  acceptBooking,
  getWorkerBookings,
  rejectBooking,
} from '../../services/bookingService';
import { getApiErrorMessage } from '../../utils/formValidationMessages';
import { PageIntro, LoadingPanel } from '../../components/ui/PortalPrimitives';
import SystemError from '../../components/common/SystemError';
import { useToast } from '../../components/common/ToastContext';

const formatWhen = (b) => {
  const d = b.bookingDate || '';
  const a = (b.startTime || '').slice(0, 5);
  const z = (b.endTime || '').slice(0, 5);
  return `${d} · ${a}–${z}`;
};

const WorkerBookingsPage = () => {
  const toast = useToast();
  const [rows, setRows] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const load = useCallback(async () => {
    setLoading(true);
    setError('');
    try {
      const data = await getWorkerBookings();
      setRows(Array.isArray(data) ? data : []);
    } catch (err) {
      setError(getApiErrorMessage(err, 'Could not load bookings.'));
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    load();
  }, [load]);

  const onAccept = async (id) => {
    try {
      await acceptBooking(id);
      toast.success('Booking accepted.');
      load();
    } catch (err) {
      toast.error(getApiErrorMessage(err, 'Could not accept.'));
    }
  };

  const onReject = async (id) => {
    if (!window.confirm('Decline this booking request?')) return;
    try {
      await rejectBooking(id);
      toast.info('Booking declined.');
      load();
    } catch (err) {
      toast.error(getApiErrorMessage(err, 'Could not decline.'));
    }
  };

  const pending = rows.filter((b) => b.status === 'PENDING');
  const rest = rows.filter((b) => b.status !== 'PENDING');

  if (loading) {
    return (
      <div className="page-wrapper">
        <main className="ui-shell">
          <LoadingPanel message="Loading bookings…" />
        </main>
      </div>
    );
  }

  if (error) {
    return (
      <div className="page-wrapper">
        <main className="ui-shell max-w-2xl">
          <SystemError title="Couldn’t load bookings" message={error} onRetry={load} />
        </main>
      </div>
    );
  }

  const renderCard = (b) => (
    <li key={b.id} className="ui-panel p-5">
      <div className="flex flex-col gap-3 lg:flex-row lg:items-start lg:justify-between">
        <div>
          <p className="text-sm text-ink-muted">Seeker</p>
          <p className="font-bold text-ink">{b.seekerName || 'Seeker'}</p>
          <p className="mt-1 text-sm text-ink-muted">{formatWhen(b)}</p>
          {b.note ? <p className="mt-2 text-sm text-ink-soft">{b.note}</p> : null}
          <p className="mt-2 text-xs font-bold uppercase tracking-wider text-ink-subtle">
            {b.status}
          </p>
        </div>
        <div className="flex flex-col gap-2 sm:flex-row lg:flex-col lg:items-end">
          {b.seekerPhone ? (
            <a
              href={`tel:${String(b.seekerPhone).replace(/\s+/g, '')}`}
              className="ui-button-primary inline-flex justify-center"
            >
              <span className="material-icons text-base">phone</span>
              {b.seekerPhone}
            </a>
          ) : null}
          {b.status === 'PENDING' ? (
            <div className="flex flex-wrap gap-2">
              <button type="button" className="ui-button-primary text-sm" onClick={() => onAccept(b.id)}>
                Accept
              </button>
              <button type="button" className="ui-button-ghost text-sm" onClick={() => onReject(b.id)}>
                Decline
              </button>
            </div>
          ) : null}
        </div>
      </div>
    </li>
  );

  return (
    <div className="page-wrapper">
      <main className="ui-shell space-y-8">
        <PageIntro
          eyebrow="Schedule"
          title="Bookings"
          subtitle="See who booked a time with you. Confirm or decline based on your schedule. After you accept, they can see your number to call you, and you can call them on the number shown here."
          light
        />

        {rows.length === 0 ? (
          <div className="ui-panel p-8 text-center text-ink-muted">
            <p>No booking requests yet.</p>
            <p className="mt-2 text-sm">When a seeker books a slot with you, it will show up here.</p>
          </div>
        ) : (
          <>
            {pending.length > 0 ? (
              <section>
                <h2 className="mb-3 text-sm font-bold uppercase tracking-[0.2em] text-white/80">Needs your response</h2>
                <ul className="space-y-4">{pending.map(renderCard)}</ul>
              </section>
            ) : null}
            {rest.length > 0 ? (
              <section>
                <h2 className="mb-3 text-sm font-bold uppercase tracking-[0.2em] text-white/80">Other</h2>
                <ul className="space-y-4">{rest.map(renderCard)}</ul>
              </section>
            ) : null}
          </>
        )}
      </main>
    </div>
  );
};

export default WorkerBookingsPage;
