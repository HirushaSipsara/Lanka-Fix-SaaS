import React, { useCallback, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getSeekerBookings, cancelBooking } from '../../services/bookingService';
import { getApiErrorMessage } from '../../utils/formValidationMessages';
import { PageIntro, LoadingPanel } from '../../components/ui/PortalPrimitives';
import SystemError from '../../components/common/SystemError';
import { useToast } from '../../components/common/ToastContext';

const statusLabel = (s) => {
  if (s === 'PENDING') return { text: 'Awaiting worker', tone: 'warning' };
  if (s === 'ACCEPTED') return { text: 'Accepted', tone: 'success' };
  if (s === 'REJECTED') return { text: 'Declined', tone: 'danger' };
  if (s === 'CANCELLED') return { text: 'Cancelled', tone: 'neutral' };
  return { text: s, tone: 'neutral' };
};

const formatWhen = (b) => {
  const d = b.bookingDate || '';
  const a = (b.startTime || '').slice(0, 5);
  const z = (b.endTime || '').slice(0, 5);
  return `${d} · ${a}–${z}`;
};

const MyBookingsPage = () => {
  const toast = useToast();
  const [rows, setRows] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const load = useCallback(async () => {
    setLoading(true);
    setError('');
    try {
      const data = await getSeekerBookings();
      setRows(Array.isArray(data) ? data : []);
    } catch (err) {
      setError(getApiErrorMessage(err, 'Could not load your bookings.'));
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    load();
  }, [load]);

  const onCancel = async (id) => {
    if (!window.confirm('Cancel this booking request?')) return;
    try {
      await cancelBooking(id);
      toast.success('Booking cancelled.');
      load();
    } catch (err) {
      toast.error(getApiErrorMessage(err, 'Could not cancel.'));
    }
  };

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

  return (
    <div className="page-wrapper">
      <main className="ui-shell space-y-6">
        <PageIntro
          eyebrow="Bookings"
          title="My bookings"
          subtitle="Direct time slots you requested with workers. After a worker accepts, their phone number appears here so you can call them."
          light
        />

        {rows.length === 0 ? (
          <div className="ui-panel p-8 text-center text-ink-muted">
            <p>You have no bookings yet.</p>
            <Link to="/browse-workers" className="ui-button-primary mt-4 inline-flex">Browse workers</Link>
          </div>
        ) : (
          <ul className="space-y-4">
            {rows.map((b) => {
              const st = statusLabel(b.status);
              return (
                <li key={b.id} className="ui-panel p-5">
                  <div className="flex flex-col gap-3 sm:flex-row sm:items-start sm:justify-between">
                    <div>
                      <p className="font-bold text-ink">{b.workerName || 'Worker'}</p>
                      <p className="mt-1 text-sm text-ink-muted">{formatWhen(b)}</p>
                      {b.note ? <p className="mt-2 text-sm text-ink-soft">{b.note}</p> : null}
                      <span className={`mt-2 inline-flex rounded-chip border px-3 py-1 text-xs font-bold uppercase tracking-wider ${
                        st.tone === 'success' ? 'border-green-200 bg-green-50 text-green-800' :
                        st.tone === 'warning' ? 'border-amber-200 bg-amber-50 text-amber-900' :
                        st.tone === 'danger' ? 'border-red-200 bg-red-50 text-red-800' :
                        'border-line bg-surface-muted text-ink-muted'
                      }`}
                      >
                        {st.text}
                      </span>
                    </div>
                    <div className="flex flex-col gap-2 sm:items-end">
                      {b.status === 'ACCEPTED' && b.workerPhone ? (
                        <a
                          href={`tel:${String(b.workerPhone).replace(/\s+/g, '')}`}
                          className="ui-button-primary inline-flex w-full justify-center sm:w-auto"
                        >
                          <span className="material-icons text-base">phone</span>
                          Call {b.workerPhone}
                        </a>
                      ) : null}
                      {b.status === 'PENDING' ? (
                        <button type="button" className="ui-button-ghost text-sm" onClick={() => onCancel(b.id)}>
                          Cancel request
                        </button>
                      ) : null}
                    </div>
                  </div>
                </li>
              );
            })}
          </ul>
        )}
      </main>
    </div>
  );
};

export default MyBookingsPage;
