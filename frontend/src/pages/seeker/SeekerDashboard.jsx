import React, { useEffect, useMemo, useState } from 'react';
import { Link } from 'react-router-dom';
import { getCurrentUser } from '../../services/authService';
import { getMyRequests } from '../../services/requestService';
import {
  AlertPanel,
  EmptyState,
  LoadingPanel,
  PageIntro,
  SectionCard,
  StatCard,
  StatusPill,
} from '../../components/ui/PortalPrimitives';
import { formatCategoryLabel } from '../../utils/constants';

const statusTone = (status) => {
  const normalized = String(status || '').toUpperCase();
  if (normalized === 'OPEN') return 'info';
  if (normalized === 'ASSIGNED' || normalized === 'IN_PROGRESS') return 'warning';
  if (normalized === 'COMPLETED') return 'success';
  if (normalized === 'CANCELLED' || normalized === 'NOT_COMPLETED') return 'danger';
  return 'neutral';
};

const prettyStatus = (status) => String(status || 'Unknown').replaceAll('_', ' ');

const formatDate = (dateString) => {
  if (!dateString) return 'Recently updated';
  return new Date(dateString).toLocaleDateString('en-US', {
    month: 'short',
    day: 'numeric',
    year: 'numeric',
  });
};

const excerpt = (text, maxLength = 140) => {
  if (!text) return 'Add more detail to help workers understand the job and quote accurately.';
  return text.length > maxLength ? `${text.slice(0, maxLength).trim()}...` : text;
};

const firstName = (name) => {
  if (!name) return 'there';
  return name.trim().split(/\s+/)[0];
};

const SeekerDashboard = () => {
  const currentUser = getCurrentUser();
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const loadDashboard = async () => {
    setLoading(true);
    setError('');

    try {
      const data = await getMyRequests();
      setRequests(Array.isArray(data) ? data : []);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to load your dashboard. Please try again.');
      setRequests([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadDashboard();
  }, []);

  const stats = useMemo(() => {
    const summary = {
      total: requests.length,
      open: 0,
      active: 0,
      completed: 0,
      attention: 0,
    };

    requests.forEach((request) => {
      const status = String(request.status || '').toUpperCase();
      if (status === 'OPEN') summary.open += 1;
      else if (status === 'ASSIGNED' || status === 'IN_PROGRESS') summary.active += 1;
      else if (status === 'COMPLETED') summary.completed += 1;
      else if (status === 'NOT_COMPLETED' || status === 'CANCELLED') summary.attention += 1;
    });

    return summary;
  }, [requests]);

  const recentRequests = useMemo(() => (
    [...requests].sort((left, right) => {
      const leftTime = new Date(left.updatedAt || left.createdAt || 0).getTime();
      const rightTime = new Date(right.updatedAt || right.createdAt || 0).getTime();
      return rightTime - leftTime;
    }).slice(0, 5)
  ), [requests]);

  const activeCount = stats.open + stats.active;
  const hasRequests = requests.length > 0;
  const overviewTiles = [
    {
      label: 'Awaiting Quotes',
      value: stats.open,
      tone: 'border-blue-100 bg-blue-50/70 text-blue-900',
    },
    {
      label: 'Active Jobs',
      value: stats.active,
      tone: 'border-amber-100 bg-amber-50/75 text-amber-900',
    },
    {
      label: 'Completed',
      value: stats.completed,
      tone: 'border-green-100 bg-green-50/75 text-green-900',
    },
  ];

  return (
    <div className="page-wrapper">
      <main className="ui-shell space-y-5">
        <PageIntro
          eyebrow="Seeker Dashboard"
          title={`Welcome back, ${firstName(currentUser?.fullName)}`}
          subtitle="Stay on top of every request, spot urgent follow-ups quickly, and jump back into the jobs that need your attention."
          light
          actions={(
            <Link to="/create-request" className="ui-button-primary w-full sm:w-auto">
              <span className="material-icons text-base">add</span>
              Create New Request
            </Link>
          )}
        />

        <section className="overflow-hidden rounded-panel border-2 border-white/80 bg-white/95 p-5 shadow-panel backdrop-blur-xl lg:p-6">
          <div className="grid gap-5 xl:grid-cols-[minmax(0,1.4fr)_320px] xl:items-start">
            <div className="space-y-4">
              <span className="ui-badge border-brand-300 bg-brand-50 text-brand-900 shadow-sm">Overview</span>
              <div className="space-y-2">
                <h2 className="max-w-3xl font-display text-[2rem] font-extrabold leading-[1.05] tracking-snugger text-ink md:text-[2.65rem]">
                  {activeCount > 0 ? (
                    <>
                      <span className="bg-brand-gradient bg-clip-text text-transparent">
                        {activeCount}
                      </span>
                      <span className="ml-2 inline-block">
                        Active Request{activeCount === 1 ? '' : 's'} in motion
                      </span>
                    </>
                  ) : (
                    'Your workspace is ready for the next job'
                  )}
                </h2>
                <p className="max-w-3xl text-sm leading-7 text-ink-soft">
                  Use this space to monitor open jobs, track work in progress, and revisit completed requests without scanning multiple pages.
                </p>
              </div>

              <div className="grid gap-3 sm:grid-cols-3">
                {overviewTiles.map((tile) => (
                  <div key={tile.label} className={`rounded-card border-2 px-4 py-3 shadow-md ${tile.tone}`}>
                    <p className="ui-stat-label !text-current/80">{tile.label}</p>
                    <p className="mt-2 text-2xl font-extrabold tracking-tight">{tile.value}</p>
                  </div>
                ))}
              </div>
            </div>

            <div className="rounded-panel border-2 border-brand-300 bg-gradient-to-br from-white to-brand-50 p-4 shadow-card">
              <p className="ui-stat-label">Next Best Action</p>
              <h3 className="mt-2 text-lg font-bold text-ink">
                {hasRequests ? 'Review your most recent request activity' : 'Post your first service request'}
              </h3>
              <p className="mt-2 text-sm leading-6 text-ink-muted">
                {hasRequests
                  ? 'Open your latest request to keep details current and make it easier for workers to respond.'
                  : 'A clear request with budget, location, and urgency helps workers quote faster and more accurately.'}
              </p>
              <div className="mt-4 flex flex-col gap-2 sm:flex-row sm:flex-wrap">
                <Link to={hasRequests ? `/my-requests/${recentRequests[0]?.id}` : '/create-request'} className="ui-button-primary w-full sm:w-auto">
                  <span className="material-icons text-base">{hasRequests ? 'visibility' : 'add'}</span>
                  {hasRequests ? 'Open Latest Request' : 'Create Request'}
                </Link>
                <Link to="/my-requests" className="ui-button-secondary w-full sm:w-auto">
                  <span className="material-icons text-base">dashboard</span>
                  View All Requests
                </Link>
              </div>
            </div>
          </div>
        </section>

        <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-4">
          <div className="rounded-card border-2 border-brand-200 bg-gradient-to-br from-white to-brand-50 p-5 shadow-card transition-all hover:-translate-y-1 hover:shadow-panel hover:border-brand-300">
            <div className="flex items-start justify-between">
              <div className="flex-1">
                <p className="ui-stat-label">Total Requests</p>
                <p className="ui-stat-value">{stats.total}</p>
                <p className="mt-2 text-xs leading-5 text-ink-muted">Every service request you have posted so far.</p>
              </div>
              <span className="flex h-12 w-12 shrink-0 items-center justify-center rounded-xl bg-brand-gradient text-white shadow-brand">
                <span className="material-icons">assignment</span>
              </span>
            </div>
          </div>
          <div className="rounded-card border-2 border-accent-200 bg-gradient-to-br from-white to-accent-50 p-5 shadow-card transition-all hover:-translate-y-1 hover:shadow-panel hover:border-accent-300">
            <div className="flex items-start justify-between">
              <div className="flex-1">
                <p className="ui-stat-label">Awaiting Quotes</p>
                <p className="ui-stat-value">{stats.open}</p>
                <p className="mt-2 text-xs leading-5 text-ink-muted">Requests that are open and ready for worker responses.</p>
              </div>
              <span className="flex h-12 w-12 shrink-0 items-center justify-center rounded-xl bg-gradient-accent text-white shadow-md">
                <span className="material-icons">hourglass_top</span>
              </span>
            </div>
          </div>
          <div className="rounded-card border-2 border-amber-200 bg-gradient-to-br from-white to-amber-50 p-5 shadow-card transition-all hover:-translate-y-1 hover:shadow-panel hover:border-amber-300">
            <div className="flex items-start justify-between">
              <div className="flex-1">
                <p className="ui-stat-label">Work In Progress</p>
                <p className="ui-stat-value">{stats.active}</p>
                <p className="mt-2 text-xs leading-5 text-ink-muted">Jobs that already have a worker assigned or are underway.</p>
              </div>
              <span className="flex h-12 w-12 shrink-0 items-center justify-center rounded-xl bg-gradient-to-br from-amber-400 to-amber-600 text-white shadow-md">
                <span className="material-icons">construction</span>
              </span>
            </div>
          </div>
          <div className="rounded-card border-2 border-green-200 bg-gradient-to-br from-white to-green-50 p-5 shadow-card transition-all hover:-translate-y-1 hover:shadow-panel hover:border-green-300">
            <div className="flex items-start justify-between">
              <div className="flex-1">
                <p className="ui-stat-label">Completed Jobs</p>
                <p className="ui-stat-value">{stats.completed}</p>
                <p className="mt-2 text-xs leading-5 text-ink-muted">Requests that have been successfully completed.</p>
              </div>
              <span className="flex h-12 w-12 shrink-0 items-center justify-center rounded-xl bg-gradient-to-br from-green-400 to-green-600 text-white shadow-md">
                <span className="material-icons">task_alt</span>
              </span>
            </div>
          </div>
        </div>

        {loading ? <LoadingPanel message="Loading your dashboard..." /> : null}

        {!loading && error ? (
          <AlertPanel
            tone="danger"
            icon="error_outline"
            title="Couldn’t load your dashboard"
            action={<button onClick={loadDashboard} className="ui-button-primary" type="button">Try Again</button>}
          >
            <p>{error}</p>
          </AlertPanel>
        ) : null}

        {!loading && !error && !hasRequests ? (
          <EmptyState
            icon="assignment_late"
            title="No requests yet"
            text="Create your first service request to start receiving quotations from workers near you."
            action={<Link to="/create-request" className="ui-button-primary">Create Request</Link>}
          />
        ) : null}

        {!loading && !error && hasRequests ? (
          <div className="grid gap-5 xl:grid-cols-[minmax(0,1.45fr)_320px]">
            <div className="overflow-hidden rounded-panel border-2 border-white/90 bg-white/95 shadow-panel backdrop-blur-lg">
              <div className="border-b-2 border-brand-200 bg-gradient-to-r from-white to-brand-50 px-4 py-4 sm:px-5">
                <div className="flex flex-col gap-2 sm:flex-row sm:items-end sm:justify-between">
                  <div>
                    <p className="ui-stat-label">Recent Activity</p>
                    <h2 className="mt-2 text-xl font-bold text-ink sm:text-2xl">Recent Requests</h2>
                  </div>
                  <p className="text-sm font-medium text-ink-muted">
                    Quickly scan status, timing, and location for your latest jobs.
                  </p>
                </div>
              </div>

              <div className="divide-y divide-line/50">
                {recentRequests.map((request) => (
                  <article key={request.id} className="bg-white/60 px-4 py-4 transition hover:bg-white sm:px-5">
                    <div className="flex flex-col gap-4 lg:flex-row lg:items-start lg:justify-between">
                      <div className="min-w-0 space-y-2">
                        <div className="flex flex-wrap items-center gap-2">
                          <span className="ui-badge border-brand-300 bg-brand-50">{formatCategoryLabel(request.category)}</span>
                          <StatusPill tone={statusTone(request.status)}>{prettyStatus(request.status)}</StatusPill>
                        </div>
                        <div>
                          <h3 className="text-xl font-bold leading-tight text-ink sm:text-2xl">
                            {request.title || formatCategoryLabel(request.category)}
                          </h3>
                          <p className="mt-2 max-w-3xl text-sm leading-6 text-ink-muted">
                            {excerpt(request.description)}
                          </p>
                        </div>
                      </div>

                      <div className="grid min-w-full gap-3 sm:grid-cols-2 lg:min-w-[280px] lg:max-w-[320px]">
                        <div className="rounded-card border border-white/60 bg-white/50 px-4 py-3 shadow-soft backdrop-blur-sm">
                          <p className="ui-stat-label">Updated</p>
                          <p className="mt-2 text-sm font-semibold text-ink">{formatDate(request.updatedAt || request.createdAt)}</p>
                        </div>
                        <div className="rounded-card border border-white/60 bg-white/50 px-4 py-3 shadow-soft backdrop-blur-sm">
                          <p className="ui-stat-label">Location</p>
                          <p className="mt-2 text-sm font-semibold text-ink">{request.locationArea || 'Not set'}</p>
                        </div>
                        <div className="rounded-card border border-white/60 bg-white/50 px-4 py-3 shadow-soft backdrop-blur-sm sm:col-span-2 lg:col-span-1">
                          <p className="ui-stat-label">Urgency</p>
                          <p className="mt-2 text-sm font-semibold text-ink">{prettyStatus(request.urgency || 'MEDIUM')}</p>
                        </div>
                      </div>
                    </div>

                    <div className="mt-4 flex flex-col gap-3 border-t border-white/40 pt-3 sm:flex-row sm:items-center sm:justify-between">
                      <p className="text-sm font-medium leading-6 text-ink-muted">
                        Open the request to view worker responses, update details, or manage the job.
                      </p>
                      <Link to={`/my-requests/${request.id}`} className="ui-button-primary w-full sm:w-auto">
                        View Request
                        <span className="material-icons text-base">arrow_forward</span>
                      </Link>
                    </div>
                  </article>
                ))}
              </div>
            </div>

            <aside className="space-y-5">
              <div className="rounded-panel border-2 border-brand-200 bg-gradient-to-br from-white to-brand-50 p-5 shadow-card">
                <p className="ui-stat-label">Need Attention</p>
                <h2 className="mt-2 text-xl font-bold text-ink">Priority Snapshot</h2>
                <div className="mt-4 space-y-3">
                  <div className="rounded-card border-2 border-blue-200 bg-gradient-to-br from-blue-50 to-blue-100 px-4 py-3.5">
                    <p className="text-sm font-semibold text-blue-900">Open Requests</p>
                    <p className="mt-2 text-2xl font-extrabold text-blue-900">{stats.open}</p>
                    <p className="mt-2 text-sm leading-6 text-blue-800">Waiting for worker interest and fresh quotations.</p>
                  </div>
                  <div className="rounded-card border-2 border-amber-200 bg-gradient-to-br from-amber-50 to-amber-100 px-4 py-3.5">
                    <p className="text-sm font-semibold text-amber-900">Jobs In Progress</p>
                    <p className="mt-2 text-2xl font-extrabold text-amber-900">{stats.active}</p>
                    <p className="mt-2 text-sm leading-6 text-amber-800">Keep an eye on active work and confirm outcomes when finished.</p>
                  </div>
                  <div className="rounded-card border-2 border-red-200 bg-gradient-to-br from-red-50 to-red-100 px-4 py-3.5">
                    <p className="text-sm font-semibold text-red-900">Needs Follow-up</p>
                    <p className="mt-2 text-2xl font-extrabold text-red-900">{stats.attention}</p>
                    <p className="mt-2 text-sm leading-6 text-red-800">Cancelled or incomplete requests that may need a new plan.</p>
                  </div>
                </div>
              </div>

              <div className="rounded-panel border-2 border-brand-200 bg-gradient-to-br from-white to-brand-50 p-5 shadow-card">
                <p className="ui-stat-label">Quick Actions</p>
                <h2 className="mt-2 text-xl font-bold text-ink">Move Faster</h2>
                <div className="mt-4 space-y-3">
                  <Link to="/create-request" className="flex items-start gap-3 rounded-card border-2 border-brand-200 bg-white px-4 py-3.5 transition hover:border-brand-400 hover:bg-brand-50 hover:shadow-md">
                    <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-2xl bg-brand-gradient text-white shadow-brand">
                      <span className="material-icons">playlist_add</span>
                    </span>
                    <span className="min-w-0">
                      <span className="block text-sm font-bold text-ink">Post a new request</span>
                      <span className="mt-1 block text-sm leading-6 text-ink-muted">Start a fresh job with clear details and urgency.</span>
                    </span>
                  </Link>

                  <Link to="/browse-workers" className="flex items-start gap-3 rounded-card border-2 border-brand-200 bg-white px-4 py-3.5 transition hover:border-brand-400 hover:bg-brand-50 hover:shadow-md">
                    <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-2xl bg-gradient-to-br from-brand-200 to-brand-400 text-white shadow-sm">
                      <span className="material-icons">groups</span>
                    </span>
                    <span className="min-w-0">
                      <span className="block text-sm font-bold text-ink">Browse workers</span>
                      <span className="mt-1 block text-sm leading-6 text-ink-muted">Explore skilled professionals before assigning your next job.</span>
                    </span>
                  </Link>

                  <Link to="/my-reviews" className="flex items-start gap-3 rounded-card border-2 border-brand-200 bg-white px-4 py-3.5 transition hover:border-brand-400 hover:bg-brand-50 hover:shadow-md">
                    <span className="flex h-10 w-10 shrink-0 items-center justify-center rounded-2xl bg-gradient-to-br from-amber-200 to-amber-400 text-amber-900 shadow-sm">
                      <span className="material-icons">rate_review</span>
                    </span>
                    <span className="min-w-0">
                      <span className="block text-sm font-bold text-ink">My reviews</span>
                      <span className="mt-1 block text-sm leading-6 text-ink-muted">View all feedback you have submitted for completed jobs.</span>
                    </span>
                  </Link>
                </div>
              </div>
            </aside>
          </div>
        ) : null}
      </main>
    </div>
  );
};

export default SeekerDashboard;
